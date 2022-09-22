package me.slavita.construction.player.events

import me.func.mod.Anime
import me.func.mod.util.after
import me.func.protocol.ui.indicator.Indicators
import me.slavita.construction.app
import me.slavita.construction.connection.ConnectionUtil.createChannel
import me.slavita.construction.multichat.MultiChatUtil
import me.slavita.construction.world.market.Market
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinEvents : Listener {
    @EventHandler
    fun PlayerJoinEvent.handle() {
        MultiChatUtil.sendPlayerChats(player)
        app.addUser(player)
        player.teleport(app.mainWorld.getSpawn())
        createChannel(player)
        after (2) {
            Anime.hideIndicator(player, Indicators.HEALTH, Indicators.EXP, Indicators.HUNGER)
        }
        after(2) {
            Market.sendPlayer(player)
        }
    }
}