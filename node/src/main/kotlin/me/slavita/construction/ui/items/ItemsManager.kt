package me.slavita.construction.ui.items

import dev.implario.bukkit.item.ItemBuilder
import me.slavita.construction.action.command.menu.general.ControlPanelMenu
import me.slavita.construction.action.command.menu.donate.DonateMenu
import me.slavita.construction.utils.listener
import org.bukkit.ChatColor.*
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.util.*

object ItemsManager {
    private val actions = hashMapOf<UUID, HashMap<ItemStack, (player: Player) -> Unit>>()
    private val ITEMS = listOf(
        ActionableItem(
            7, ItemBuilder(Material.CLAY_BALL)
                .text("${GREEN}${BOLD}Меню")
                .nbt("other", "info")
                .build()
        )
        {
            ControlPanelMenu(it).tryExecute()
        },
        ActionableItem(
            8, ItemBuilder(Material.CLAY_BALL)
                .text("${GOLD}${BOLD}Платные возможности")
                .nbt("other", "bank")
                .build()
        )
        {
            DonateMenu(it).tryExecute()
        }
    )

    init {
        listener<PlayerInteractEvent> {
            if (action == Action.PHYSICAL) return@listener
            val execute = actions[player.uniqueId]?.get(item) ?: return@listener
            execute(player)
        }
        listener<InventoryClickEvent> {
            if (ITEMS.map { it.inventoryId }.contains(slot)) isCancelled = true
        }
    }

    fun registerPlayer(player: Player) {
        actions[player.uniqueId] = hashMapOf()
        updatePlayerInventory(player)
    }

    private fun registerItem(player: Player, item: ItemStack, action: (player: Player) -> Unit) {
        actions[player.uniqueId]!![item] = action
    }

    private fun updatePlayerInventory(player: Player) {
        ITEMS.forEach { item ->
            registerItem(player, item.item, item.action)
            player.inventory.setItem(item.inventoryId, item.item)
        }
    }
}