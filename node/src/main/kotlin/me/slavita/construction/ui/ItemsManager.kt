package me.slavita.construction.ui

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.util.*

object ItemsManager : Listener {
    val actions = hashMapOf<UUID, HashMap<ItemStack, () -> Unit>>()

    fun registerItem(player: Player, item: ItemStack, action: () -> Unit) {
        actions[player.uniqueId]!![item] = action
    }

    @EventHandler
    fun PlayerInteractEvent.handle() {
        if (!listOf(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK,
                Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK).contains(action)) return

        val execute = actions[player.uniqueId]?.get(item) ?: return
        execute()
    }
}