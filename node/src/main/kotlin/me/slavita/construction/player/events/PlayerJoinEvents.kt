package me.slavita.construction.player.events

import me.slavita.construction.app
import me.slavita.construction.multichat.MultiChatUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinEvents : Listener {
    @EventHandler
    fun PlayerJoinEvent.handle() {
        MultiChatUtil.sendPlayerChats(player)
        app.addUser(player)
        preparePlayer(player)
    }

    @EventHandler
    fun PlayerInteractEvent.handle() {
        println(action)
        println(blockFace)
        println(blockClicked)
    }

    private fun preparePlayer(player: Player) {
        player.teleport(app.mainWorld.getSpawn())
        app.mainWorld.showAll(player)
    }
}