package me.slavita.construction.mod.utils.extensions

import dev.xdark.clientapi.inventory.InventoryPlayer
import dev.xdark.clientapi.item.ItemStack

object InventoryExtensions {
    fun InventoryPlayer.blocksCount(target: ItemStack, needCheckData: Boolean): Int {
        var count = 0
        (0..35).forEach {
            val item = getStackInSlot(it) ?: return@forEach
            if (item.item == null) return@forEach
            if (item.item.id != target.item.id) return@forEach
            if (!needCheckData || item.metadata == target.metadata) count += item.count
        }
        return count
    }

    fun InventoryPlayer.hotbarEqualSlots(target: ItemStack, needCheckData: Boolean): ArrayList<Int> {
        val list = arrayListOf<Int>()

        (0..8).forEach {
            val item = getStackInSlot(it) ?: return@forEach
            if (item.item == null) return@forEach
            if (item.item.id != target.item.id) return@forEach
            if (!needCheckData || item.metadata == target.metadata) list.add(it)
        }

        return list
    }

    fun InventoryPlayer.handItemEquals(targetItem: ItemStack, needCheckData: Boolean): Boolean {
        if (currentItem.item == null) return false
        if (currentItem.item.id != targetItem.item.id) return false
        return (!needCheckData || currentItem.metadata == targetItem.metadata)
    }
}