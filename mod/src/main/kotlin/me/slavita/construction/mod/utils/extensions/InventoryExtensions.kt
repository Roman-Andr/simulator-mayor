package me.slavita.construction.mod.utils.extensions

import dev.xdark.clientapi.inventory.InventoryPlayer
import dev.xdark.clientapi.item.ItemStack

object InventoryExtensions {
	fun InventoryPlayer.blocksCount(target: ItemStack): Int {
		var count = 0
		(0..35).forEach {
			val item = getStackInSlot(it) ?: return@forEach
			if (item.item != null && item.item.id == target.item.id && item.metadata == target.metadata) count += item.count
		}
		return count
	}

	fun InventoryPlayer.hotbarEqualSlots(target: ItemStack): ArrayList<Int> {
		val list = arrayListOf<Int>()

		(0..8).forEach {
			val item = getStackInSlot(it) ?: return@forEach
			if (item.item != null && item.item.id == target.item.id && item.metadata == target.metadata) list.add(it)
		}

		return list
	}

	fun InventoryPlayer.handItemEquals(targetItem: ItemStack): Boolean {
		return currentItem.item != null && currentItem.metadata == targetItem.metadata && currentItem.item.id == targetItem.item.id
	}
}