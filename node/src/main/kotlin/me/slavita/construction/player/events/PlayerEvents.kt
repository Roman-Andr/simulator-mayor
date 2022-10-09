package me.slavita.construction.player.events

import me.func.mod.util.after
import me.slavita.construction.app
import me.slavita.construction.prepare.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

object PlayerEvents : Listener {
    @EventHandler
    fun PlayerJoinEvent.handle() {
        after (2) {
            app.addUser(player).run {
                listOf(
                    PlayerWorldPrepare,
                    ConnectionPrepare,
                    PermissionsPrepare,
                    UIPrepare,
                    ItemCallbacksPrepare,
                    ShowcasePrepare,
                ).forEach { it.prepare(this) }
            }
        }
    }

    @EventHandler
    fun PlayerMoveEvent.handle() {
        app.getUser(player).run {
            if (watchableProject != null && !watchableProject!!.structure.structure.box.contains(
                    player.location,
                    watchableProject!!.structure.allocation
                )
            ) {
                watchableProject!!.structure.hideVisual()
                watchableProject = null
            }

            if (watchableProject == null) {
                activeProjects.forEach {
                    if (it.structure.structure.box.contains(player.location, it.structure.allocation)) {
                        watchableProject = it
                        it.structure.showVisual()
                        return@forEach
                    }
                }
            }
        }
    }
}