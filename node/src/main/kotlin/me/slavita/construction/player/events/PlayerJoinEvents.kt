package me.slavita.construction.player.events

import me.func.mod.Anime
import me.func.mod.util.after
import me.func.protocol.ui.indicator.Indicators
import me.slavita.construction.app
import me.slavita.construction.connection.ConnectionUtil.createChannel
import me.slavita.construction.multichat.MultiChatUtil
import me.slavita.construction.utils.ScoreBoardGenerator
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import ru.cristalix.core.permissions.IPermissionService
import ru.cristalix.core.permissions.StaffGroups

class PlayerJoinEvents : Listener {
    @EventHandler
    fun PlayerJoinEvent.handle() {
        MultiChatUtil.sendPlayerChats(player)
        app.addUser(player)
        player.teleport(app.mainWorld.getSpawn())
        createChannel(player)
        if (app.localStaff.contains(player.uniqueId)) {
            IPermissionService.get().getPermissionContextDirect(player.uniqueId).displayGroup = StaffGroups.LOCAL_STAFF
            player.isOp = true
            player.allowFlight = true
        }
        after (1) {
            Anime.hideIndicator(player, Indicators.HEALTH, Indicators.EXP, Indicators.HUNGER)
            player.gameMode = GameMode.ADVENTURE
            ScoreBoardGenerator.generate(player)
        }
    }
}