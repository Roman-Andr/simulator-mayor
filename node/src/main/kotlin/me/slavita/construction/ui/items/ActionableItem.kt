package me.slavita.construction.ui.items

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ActionableItem(
	val inventoryId: Int,
	val item: ItemStack,
	val action: (player: Player) -> Unit,
)