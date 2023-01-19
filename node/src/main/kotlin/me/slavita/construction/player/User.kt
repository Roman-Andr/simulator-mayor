package me.slavita.construction.player

import me.func.mod.Anime
import me.func.mod.conversation.ModTransfer
import me.slavita.construction.action.command.menu.city.BuyCityConfirm
import me.slavita.construction.action.command.menu.city.ShowcaseMenu
import me.slavita.construction.action.command.menu.project.ChoiceProject
import me.slavita.construction.action.command.menu.project.ChoiceStructureGroup
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.listener.OnActions
import me.slavita.construction.prepare.StoragePrepare
import me.slavita.construction.project.FreelanceProject
import me.slavita.construction.project.Project
import me.slavita.construction.storage.BlocksStorage
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.user
import org.bukkit.Location
import org.bukkit.entity.Player
import ru.cristalix.core.invoice.IInvoiceService
import java.util.*

class User(val uuid: UUID) {
    var initialized = false
    lateinit var player: Player
    lateinit var data: Data
    var cities = hashSetOf<City>()
    var currentCity: City = City(this, "1", "Незаданная", 0, true)
    val blocksStorage = BlocksStorage(this)
    var watchableProject: Project? = null
    var income = 0L
        set(value) {
            if (initialized) data.statistics.lastIncome = value
            field = value
        }
    val hall = CityHall(this)
    var taskId = 0
    var showcaseTaskId = 0
    var showcaseMenuTaskId = 0
    var showcaseMenu: ShowcaseMenu? = null
    var lastApprovedPosition: Location? = null
    private var criBalanceLastUpdate = 0L
    var inTrashZone = false

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
                IInvoiceService.get().getBalanceData(player.uniqueId).thenAccept { data ->
                    field = data.crystals + data.coins
                }
            }
            return field
        }

    fun tryPurchase(
        cost: Long,
        acceptAction: () -> Unit,
    ) {
        if (data.statistics.money >= cost) {
            data.statistics.money -= cost
            acceptAction()
        } else {
            player.deny("Недостаточно денег!")
            Anime.close(player)
        }
    }

    fun addExp(exp: Long) {
        data.statistics.experience += exp
//		if (exp / 10*2.0.pow(stats.level) > 0) {
//			stats.level += (exp / 10).toInt()
//			Anime.itemTitle(player, ItemIcons.get("other", "access"), "Новый уровень: ${stats.level}", "", 2.0)
//			Glow.animate(player, 2.0, GlowColor.GREEN)
//		}
    }

    fun canPurchase(cost: Long): Boolean {
        return data.statistics.money >= cost
    }

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

    fun updatePosition(): Boolean {
        cities.forEach { city ->
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

        if (player.user.blocksStorage.inBox() && !OnActions.storageEntered[player]!!) {
            ModTransfer()
                .send("storage:show", player)
            OnActions.storageEntered[player] = true
        }

        if (!player.user.blocksStorage.inBox() && OnActions.storageEntered[player]!!) {
            ModTransfer()
                .send("storage:hide", player)
            OnActions.storageEntered[player] = false
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

                if (OnActions.inZone[player] == false) ChoiceStructureGroup(player) { structure ->
                    ChoiceProject(player, structure, cell).keepHistory().tryExecute()
                }.tryExecute()
                OnActions.inZone[player] = true
                return false
            }

            OnActions.inZone[player] = false
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
