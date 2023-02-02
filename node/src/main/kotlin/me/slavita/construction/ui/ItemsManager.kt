package me.slavita.construction.ui

import dev.implario.bukkit.item.ItemBuilder
import me.slavita.construction.action.command.menu.city.LeaveFreelanceConfirm
import me.slavita.construction.action.command.menu.donate.DonateMenu
import me.slavita.construction.action.command.menu.general.ControlPanelMenu
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.utils.listener
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.util.UUID

object ItemsManager : IRegistrable {
    private val actions = hashMapOf<UUID, HashMap<ItemStack, (player: Player) -> Unit>>()
    private val ITEMS = listOf(
        ActionableItem(
            7,
            ItemBuilder(Material.CLAY_BALL)
                .text("${GREEN}${BOLD}Меню")
                .nbt("skyblock", "collections")
                .build()
        ) {
            ControlPanelMenu(it).tryExecute()
        },
        ActionableItem(
            8,
            ItemBuilder(Material.CLAY_BALL)
                .text("${GOLD}${BOLD}Платные возможности")
                .nbt("other", "bank")
                .build()
        ) {
            DonateMenu(it).tryExecute()
        }
    )

    val freelanceCancel = ActionableItem(
        8,
        ItemBuilder(Material.CLAY_BALL)
            .text("${GOLD}${BOLD}Выйти")
            .nbt("other", "cancel")
            .build()
    ) { player ->
        LeaveFreelanceConfirm(player).tryExecute()
    }

    override fun register() {
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

    private fun registerItem(player: Player, item: ActionableItem) {
        actions[player.uniqueId]!![item.item] = item.action
    }

    private fun updatePlayerInventory(player: Player) {
        ITEMS.forEach { item ->
            registerItem(player, item)
            player.inventory.setItem(item.inventoryId, item.item)
        }
        registerItem(player, freelanceCancel)
    }
}
