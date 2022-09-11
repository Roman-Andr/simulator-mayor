package me.slavita.construction.game.player.events

import me.slavita.construction.app
import me.slavita.construction.multichat.MultiChatUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.spigotmc.event.player.PlayerSpawnLocationEvent

class PlayerJoinEvents : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        preparePlayer(event.player)
    }

    private fun preparePlayer(player: Player) {
        player.teleport(app.getSpawn())
    }
}