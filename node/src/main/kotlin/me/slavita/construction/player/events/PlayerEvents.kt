package me.slavita.construction.player.events

import me.func.mod.Anime
import me.func.mod.util.after
import me.func.protocol.ui.indicator.Indicators
import me.slavita.construction.app
import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.multichat.MultiChatUtil
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.ui.ItemsManager
import me.slavita.construction.ui.ScoreBoardGenerator
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import ru.cristalix.core.permissions.IPermissionService
import ru.cristalix.core.permissions.StaffGroups

class PlayerEvents : Listener {
    @EventHandler
    fun PlayerJoinEvent.handle() {
        after (5) {
            MultiChatUtil.sendPlayerChats(player)
            app.addUser(player)
            player.teleport(app.mainWorld.getSpawn())
            ConnectionUtil.createChannel(player)
            if (app.localStaff.contains(player.uniqueId)) {
                IPermissionService.get().getPermissionContextDirect(player.uniqueId).displayGroup = StaffGroups.LOCAL_STAFF
                player.isOp = true
                player.allowFlight = true
            }
            Anime.hideIndicator(player, Indicators.HEALTH, Indicators.EXP, Indicators.HUNGER)
            player.gameMode = GameMode.ADVENTURE
            ScoreBoardGenerator.generate(player)

            ItemsManager.actions[player.uniqueId] = hashMapOf()
            ItemsManager.registerItem(player, ItemIcons.get("other", "myfriends")) {
                Anime.alert(player, "Самый умный?", "Ок")
            }
            player.inventory.setItem(0, ItemIcons.get("other", "myfriends"))
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
                watchableProject!!.structure.hide()
                watchableProject = null
            }

            if (watchableProject == null) {
                activeProjects.forEach {
                    if (it.structure.structure.box.contains(player.location, it.structure.allocation)) {
                        watchableProject = it
                        it.structure.show()
                        return@forEach
                    }
                }
            }
        }
    }
}