package me.slavita.construction.player.events

import me.slavita.construction.app
import me.slavita.construction.multichat.MultiChatUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.EquipmentSlot

class PlayerJoinEvents : Listener {
    @EventHandler
    fun PlayerJoinEvent.handle() {
        MultiChatUtil.sendPlayerChats(player)
        app.addUser(player)
        preparePlayer(player)
    }

    private fun preparePlayer(player: Player) {
        player.teleport(app.mainWorld.getSpawn())
        app.mainWorld.showAll(player)
    }
}