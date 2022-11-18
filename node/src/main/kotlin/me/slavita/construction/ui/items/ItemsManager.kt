package me.slavita.construction.ui.items

import dev.implario.bukkit.item.ItemBuilder
import me.slavita.construction.action.command.menu.ControlPanelMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.util.*

object ItemsManager : Listener {
    private val actions = hashMapOf<UUID, HashMap<ItemStack, (player: Player) -> Unit>>()
    private val ITEMS = listOf(
        ActionableItem(
            0, ItemBuilder(Material.CLAY_BALL)
                .text("Меню")
                .nbt("other", "info")
                .build()
        )
        {
            ControlPanelMenu(it).tryExecute()
        }
    )

    @EventHandler
    fun PlayerInteractEvent.handle() {
        if (action == Action.PHYSICAL) return

        val execute = actions[player.uniqueId]?.get(item) ?: return
        execute(player)
    }

    fun registerPlayer(player: Player) {
        actions[player.uniqueId] = hashMapOf()
        updatePlayerInventory(player)
    }

    private fun registerItem(player: Player, item: ItemStack, action: (player: Player) -> Unit) {
        actions[player.uniqueId]!![item] = action
    }

    private fun updatePlayerInventory(player: Player) {
        player.inventory.clear()
        ITEMS.forEach {
            registerItem(player, it.item, it.action)
            player.inventory.setItem(it.inventoryId, it.item)
        }
    }
}