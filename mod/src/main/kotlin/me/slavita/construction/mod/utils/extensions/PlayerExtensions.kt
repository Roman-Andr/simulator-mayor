package me.slavita.construction.mod.utils.extensions

import dev.xdark.clientapi.entity.EntityPlayerSP
import dev.xdark.clientapi.item.ItemStack

object PlayerExtensions {
    fun EntityPlayerSP.blocksCount(target: ItemStack, needCheckData: Boolean): Int {
        if (inventory == null) return 0
        var count = 0
        (0..35).forEach {
            val item = inventory.getStackInSlot(it) ?: return@forEach
            if (item.item == null) return@forEach
            if (item.item.id != target.item.id) return@forEach
            if (!needCheckData || item.metadata == target.metadata) count += item.count
        }
        return count
    }

    fun EntityPlayerSP.hotbarEqualSlots(target: ItemStack, needCheckData: Boolean): Int {
        if (inventory == null) return 0
        var count = 0
        (0..35).forEach {
            val item = inventory.getStackInSlot(it) ?: return@forEach
            if (item.item == null) return@forEach
            if (item.item.id != target.item.id) return@forEach
            if (!needCheckData || item.metadata == target.metadata) count += item.count
        }
        return count
    }
}