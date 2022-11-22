package me.slavita.construction.player.events

import me.func.mod.util.after
import me.slavita.construction.action.command.menu.project.ChoiceStructure
import me.slavita.construction.app
import me.slavita.construction.prepare.*
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.utils.listener
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

object PlayerEvents {
    private val inZone = hashMapOf<Player, Boolean>()

    init {
        listener<PlayerJoinEvent> {
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

        listener<PlayerDropItemEvent> {
            val user = app.getUser(player)
            if (!user.blocksStorage.inBox()) {
                isCancelled = true
                return@listener
            }

            user.blocksStorage.addItem(drop.itemStack)
            drop.remove()
        }

        listener<PlayerMoveEvent> {
            app.getUser(player).run {
                if (watchableProject != null && !watchableProject!!.structure.box.contains(player.location)) {
                    watchableProject!!.onLeave()
                    watchableProject = null
                }

                currentCity.cityStructures.forEach {
                    if (it.cell.box.contains(player.location) && it.state == CityStructureState.BROKEN) {
                        it.repair()
                    }
                }

                if (watchableProject == null) {
                    currentCity.projects.forEach {
                        if (it.structure.box.contains(player.location)) {
                            watchableProject = it
                            it.onEnter()
                            return@listener
                        }
                    }

                    currentCity.cells.forEach {
                        if (it.busy || !it.box.contains(player.location)) return@forEach

                        if (inZone[player] == false) ChoiceStructure(player, it).tryExecute()
                        inZone[player] = true
                        return@listener
                    }

                    inZone[player] = false
                }
            }
        }
    }
}