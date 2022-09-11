package me.slavita.construction.player.prepare

import me.slavita.construction.app
import me.slavita.construction.multichat.MultiChatUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.spigotmc.event.player.PlayerSpawnLocationEvent

class PlayerJoinEvents : Listener {
    @EventHandler
    fun onPlayerJoin(event : PlayerJoinEvent) {
        preparePlayer(event.player)
    }

    @EventHandler
    fun onLoginJoin(event : PlayerLoginEvent) {
        preparePlayer(event.player)
    }

    @EventHandler
    fun onPlayerSpawn(event : PlayerSpawnLocationEvent) {
        preparePlayer(event.player)
    }

    private fun preparePlayer(player : Player) {
        player.teleport(app.getSpawn())
        MultiChatUtil.sendPlayerChats(player)
    }
}