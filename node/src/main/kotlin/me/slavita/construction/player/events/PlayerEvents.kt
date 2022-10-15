package me.slavita.construction.player.events

import me.func.mod.util.after
import me.slavita.construction.action.command.menu.project.ChoiceStructure
import me.slavita.construction.app
import me.slavita.construction.prepare.*
import me.slavita.construction.world.Box
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

object PlayerEvents : Listener {
    private val inZone = hashMapOf<Player, Boolean>()

    @EventHandler
    fun PlayerJoinEvent.handle() {
        after (2) {
            app.addUser(player).run {
                listOf(
                    PlayerWorldIPrepare,
                    ConnectionIPrepare,
                    PermissionsIPrepare,
                    UIIPrepare,
                    ItemCallbacksIPrepare,
                    ShowcaseIPrepare,
                    BankAccountRegister
                ).forEach { it.prepare(this) }
            }
        }
    }

    @EventHandler
    fun PlayerMoveEvent.handle() {
        app.getUser(player).run {
            if (watchableProject != null && !watchableProject!!.structure.box.contains(player.location)) {
                watchableProject!!.onLeave()
                watchableProject = null
            }

            if (watchableProject == null) {
                activeProjects.forEach {
                    if (it.structure.box.contains(player.location)) {
                        watchableProject = it
                        it.onEnter()
                        return
                    }
                }


                app.mainWorld.map.labels("place").forEach {
                    if (Box(it, it.clone().add(22.0, 48.0, 22.0)).contains(player.location)) {
                        if (inZone[player] == null || !inZone[player]!!) ChoiceStructure(player, it).tryExecute()
                        inZone[player] = true
                        return
                    }
                }

                inZone[player] = false
            }
        }
    }
}