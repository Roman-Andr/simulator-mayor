package me.slavita.construction.player.events

import me.func.mod.util.after
import me.slavita.construction.action.command.menu.project.ChoiceStructure
import me.slavita.construction.app
import me.slavita.construction.prepare.*
import me.slavita.construction.storage.BlocksStorage
import me.slavita.construction.structure.tools.CityStructureState
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

object PlayerEvents : Listener {
    private val inZone = hashMapOf<Player, Boolean>()

    @EventHandler
    fun PlayerJoinEvent.handle() {
        app.getUserOrNull(player.uniqueId)?.player = player

        after(2) {
            if (!app.hasUser(player)) app.addUser(player)
            app.getUser(player).run {
                listOf(
                    PlayerWorldPrepare,
                    ConnectionPrepare,
                    PermissionsPrepare,
                    UIPrepare,
                    ItemCallbacksPrepare,
                    ShowcasePrepare,
                    BankAccountPrepare,
                    GuidePrepare,
                    StoragePrepare
                ).forEach { it.prepare(this) }
            }
        }
    }

    @EventHandler
    fun PlayerDropItemEvent.handle() {
        if (!BlocksStorage.box.contains(player.location)) {
            isCancelled = true
            return
        }

        app.getUser(player).blocksStorage.addItem(drop.itemStack)
        drop.remove()
    }

    @EventHandler
    fun PlayerMoveEvent.handle() {
        app.getUser(player).run {
            if (watchableProject != null && !watchableProject!!.structure.box.contains(player.location)) {
                watchableProject!!.onLeave()
                watchableProject = null
            }

            city.cityStructures.forEach {
                if (it.cell.box.contains(player.location) && it.state == CityStructureState.BROKEN) {
                    it.repair()
                }
            }

            if (watchableProject == null) {
                city.projects.forEach {
                    if (it.structure.box.contains(player.location)) {
                        watchableProject = it
                        it.onEnter()
                        return
                    }
                }

                city.cells.forEach {
                    if (it.busy || !it.box.contains(player.location)) return@forEach

                    if (inZone[player] == false) ChoiceStructure(player, it).tryExecute()
                    inZone[player] = true
                    return
                }

                inZone[player] = false
            }
        }
    }
}