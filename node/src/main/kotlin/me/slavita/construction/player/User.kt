package me.slavita.construction.player

import com.google.gson.GsonBuilder
import me.func.mod.Anime
import me.func.mod.conversation.ModTransfer
import me.slavita.construction.action.command.ChangeCity
import me.slavita.construction.action.command.menu.city.BuyCityConfirm
import me.slavita.construction.action.command.menu.city.ShowcaseMenu
import me.slavita.construction.action.command.menu.project.ChoiceProject
import me.slavita.construction.action.command.menu.project.ChoiceStructureGroup
import me.slavita.construction.city.City
import me.slavita.construction.city.CityDeserializer
import me.slavita.construction.city.project.FreelanceProject
import me.slavita.construction.city.project.Project
import me.slavita.construction.city.showcase.Showcase
import me.slavita.construction.city.showcase.Showcases
import me.slavita.construction.city.storage.BlocksStorage
import me.slavita.construction.city.storage.BlocksStorageDeserializer
import me.slavita.construction.common.utils.*
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.dontate.AbilityDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.listener.OnActions
import me.slavita.construction.prepare.StoragePrepare
import me.slavita.construction.structure.CityCell
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.ui.HumanizableValues
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.user
import org.bukkit.Bukkit
import org.bukkit.ChatColor.GOLD
import org.bukkit.Location
import ru.cristalix.core.invoice.IInvoiceService
import java.util.UUID
import kotlin.math.abs

class User(val uuid: UUID) {
    val player = Bukkit.getPlayer(uuid)!!
    var watchableProject: Project? = null
    var income = 0L
        set(value) {
            data.lastIncome = value
            field = value
        }
    val showcases: HashSet<Showcase> = Showcases.showcases.map { Showcase(it) }.toHashSet()
    private var criBalanceLastUpdate = 0L
    var inTrashZone = false
    var showcaseMenu: ShowcaseMenu? = null

    lateinit var data: Data
    lateinit var currentCity: City
    lateinit var freelanceCell: CityCell

    var currentFreelance: FreelanceProject? = null
        set(value) {
            data.hasFreelance = value != null
            field = value
        }
    var showcaseMenuTaskId = 0
    private var lastApprovedPosition: Location? = null

    var criBalance: Int = 0
        get() {
            val now = System.currentTimeMillis()

            if (now - criBalanceLastUpdate > 1000 * 60) {
                criBalanceLastUpdate = now
                IInvoiceService.get().getBalanceData(uuid).thenAccept { data ->
                    field = data.crystals + data.coins
                }
            }
            return field
        }

    fun initialize(data: String?) {
        this.data = if (data == null) Data(this)
        else GsonBuilder()
            .registerTypeAdapter(City::class.java, CityDeserializer(this))
            .registerTypeAdapter(BlocksStorage::class.java, BlocksStorageDeserializer(this))
            .create()
            .fromJson(data, Data::class.java)

        this.data.user = this

        currentCity = this.data.cities.first()

        income += this.data.hall.income
    }

    fun upgradeHall() {
        data.hall.apply {
            tryPurchase(upgradePrice) {
                this@User.income -= income
                data.hall.level++
                this@User.income += income
                player.accept("Вы успешно улучшили ${GOLD}Мэрию")
            }
        }
    }

    fun tryPurchase(cost: Long, acceptAction: () -> Unit) {
        if (data.money >= cost) {
            data.money -= cost
            acceptAction()
        } else {
            Anime.close(player)
            player.deny("Недостаточно денег!")
        }
    }

    fun addExp(exp: Long) {
        data.experience += exp
    }

    fun canPurchase(cost: Long) = data.money >= cost

    fun tryChangeCity(city: City) {
        val ignore = data.abilities.contains((Donates.NO_LIMIT_TELEPORT_DONATE.donate as AbilityDonate).ability)
        ChangeCity(this, city).tryExecute(ignore).run {
            if (!ignore && this < 0) player.deny(
                "Подождите ещё ${HumanizableValues.SECOND.get((abs(this) / 20).toInt())}"
            )
        }
    }

    fun changeCity(city: City) {
        currentCity.projects.forEach { it.structure.deleteVisual() }

        player.teleport(city.getSpawn())
        currentCity = city
        data.lastCityId = currentCity.id

        StoragePrepare.prepare(this)

        currentCity.cityCells.forEach { stub ->
            runAsync(30) {
                stub.updateStub()
            }
        }

        city.projects.forEach { it.structure.visual.start() }
    }

    fun addAbility(ability: Abilities) {
        data.abilities.add(ability)
        ability.applyAction(this)
    }

    fun updatePosition(): Boolean { // TODO: rewrite it
        data.cities.forEach { city ->
            if (city.box.contains(player.location)) {
                if (currentCity.title != city.title && city.unlocked) {
                    currentCity = city
                    return false
                }
                if (!city.unlocked) {
                    player.teleport(lastApprovedPosition)
                    BuyCityConfirm(player, city, false).tryExecute()
                    return true
                } else {
                    lastApprovedPosition = player.location
                }
            }
        }
        if (watchableProject != null && !watchableProject!!.structure.box.contains(player.location)) {
            watchableProject!!.onLeave()
            watchableProject = null
        }

        if (player.user.data.blocksStorage.inBox() && !OnActions.storageEntered[player.uniqueId]!!) {
            ModTransfer().send(STORAGE_SHOW_CHANNEL, player)
            OnActions.storageEntered[player.uniqueId] = true
        }

        if (!player.user.data.blocksStorage.inBox() && OnActions.storageEntered[player.uniqueId]!!) {
            ModTransfer().send(STORAGE_HIDE_CHANNEL, player)
            OnActions.storageEntered[player.uniqueId] = false
        }

        if (watchableProject == null) {
            if (currentFreelance != null && freelanceCell.box.contains(player.location)) {
                watchableProject = currentFreelance
                watchableProject!!.onEnter()
                return false
            }

            currentCity.projects.forEach { project ->
                if (project.structure.box.contains(player.location)) {
                    watchableProject = project
                    project.onEnter()
                    return false
                }
            }

            currentCity.cityCells.forEach { cityCell ->
                if (cityCell.busy || !cityCell.box.contains(player.location)) return@forEach

                if (OnActions.inZone[player.uniqueId] == false) ChoiceStructureGroup(player) { structure ->
                    ChoiceProject(player, structure, cityCell).keepHistory().tryExecute()
                }.tryExecute()
                OnActions.inZone[player.uniqueId] = true
                return false
            }

            OnActions.inZone[player.uniqueId] = false
        } else {
            if (currentFreelance != null && freelanceCell.box.contains(player.location) && currentFreelance!!.structure.state == StructureState.FINISHED) {
                watchableProject = null
                currentFreelance!!.onEnter()
            }

            currentCity.projects.filter { it.structure.state == StructureState.FINISHED }.forEach { project ->
                if (project.structure.box.contains(player.location)) {
                    watchableProject = project
                    project.onEnter()
                    return false
                }
            }
        }
        return false
    }

    fun updateDaily() {
        val time = System.currentTimeMillis()
        data.nextDay++
        data.nextTakeDailyReward = time + 24 * 60 * 60 * 1000
    }

    fun leaveFreelance(restore: Boolean) {
        data.apply {
            if (restore) currentFreelance!!.restore()
            else hasFreelance = false

            if (reputation >= 100) reputation -= 100 else reputation = 0L
            player.deny("Вы вышли во время фриланс заказа. Штраф: 100 репутации")
        }
    }

    fun updateAchievement(type: AchievementType) {
        getAchievement(type).run {
            val value = updateAchieveValue(type)
            lastValue = value
            while (value >= type.formula(level) && level < 50) {
                level++
                expectValue = type.formula(level)
                if (data.settings.achievementsNotify) player.accept("Получено достижение ${GOLD}${type.title} #$level")
            }
        }
    }

    fun getAchievement(type: AchievementType) = data.achievements.find { it.type == type }!!

    private fun updateAchieveValue(type: AchievementType) = when (type) {
        AchievementType.MONEY         -> data.money
        AchievementType.PROJECTS      -> data.totalProjects
        AchievementType.WORKERS       -> data.workers.size
        AchievementType.CITY_HALL     -> data.hall.level
        AchievementType.STORAGE       -> data.blocksStorage.level
        AchievementType.FREELANCE     -> data.freelanceProjectsCount
        AchievementType.BOUGHT_BLOCKS -> data.boughtBlocks
    }.toLong()
}
