package me.slavita.construction.player

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.action.command.menu.city.BuyCityConfirm
import me.slavita.construction.action.command.menu.project.ChoiceStructureGroup
import me.slavita.construction.app
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.listener.OnActions
import me.slavita.construction.prepare.StoragePrepare
import me.slavita.construction.project.Project
import me.slavita.construction.storage.BlocksStorage
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.utils.PlayerExtensions.deny
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.scheduler
import me.slavita.construction.utils.user
import org.bukkit.entity.Player
import ru.cristalix.core.invoice.IInvoiceService
import java.util.*

class User(val uuid: UUID) {
    val player = Bukkit.getPlayer(uuid)!!
    var watchableProject: Project? = null
    var income = 0L
    val showcases: HashSet<Showcase> = Showcases.showcases.map { Showcase(it) }.toHashSet()
    private var criBalanceLastUpdate = 0L

    lateinit var data: Data
    lateinit var currentCity: City
    lateinit var freelanceCell: PlayerCell

    var currentFreelance: FreelanceProject? = null
        set(value) {
            data.hasFreelance = value != null
            field = value
        }

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

        currentCity = this.data.cities.first()

        income += income
    }

    fun upgradeHall() {
        data.hall.apply {
            tryPurchase(upgradePrice) {
                this@User.income -= income
                data.hall.level++
                this@User.income += income
                player.accept("Вы успешно улучшили ${ChatColor.GOLD}Мэрию")
            }
        }
    }

    fun tryPurchase(
        cost: Long,
        acceptAction: () -> Unit,
    ) {
        if (data.money >= cost) {
            data.money -= cost
            acceptAction()
        } else {
            player.deny("Недостаточно денег!")
        }
    }

    fun addExp(exp: Long) {
        data.experience += exp
//		if (exp / 10*2.0.pow(stats.level) > 0) {
//			stats.level += (exp / 10).toInt()
//			Anime.itemTitle(player, ItemIcons.get("other", "access"), "Новый уровень: ${stats.level}", "", 2.0)
//			Glow.animate(player, 2.0, GlowColor.GREEN)
//		}
    }

    fun canPurchase(cost: Long) = data.money >= cost

    fun changeCity(city: City) {
        currentCity.projects.forEach { it.structure.deleteVisual() }

        player.teleport(city.getSpawn())
        currentCity = city

        StoragePrepare.prepare(this)

        currentCity.playerCells.forEach { stub ->
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

    fun updatePosition(): Boolean { //todo: rewrite it
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

        currentCity.cityStructures.forEach {
            if (it.playerCell.box.contains(player.location) && it.state == CityStructureState.BROKEN) {
                it.repair()
            }
        }

        if (player.user.data.blocksStorage.inBox() && !OnActions.storageEntered[player.uniqueId]!!) {
            ModTransfer().send("storage:show", player)
            OnActions.storageEntered[player.uniqueId] = true
        }

        if (!player.user.data.blocksStorage.inBox() && OnActions.storageEntered[player.uniqueId]!!) {
            ModTransfer().send("storage:hide", player)
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

            currentCity.playerCells.forEach { cell ->
                if (cell.busy || !cell.box.contains(player.location)) return@forEach

                if (OnActions.inZone[player] == false) ChoiceStructureGroup(player, cell) { structure ->
                    ChoiceProject(player, structure, cell).keepHistory().tryExecute()
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
        data.statistics.nextDay++
        data.statistics.nextTakeDailyReward = time + 24 * 60 * 60 * 1000
    }
}
