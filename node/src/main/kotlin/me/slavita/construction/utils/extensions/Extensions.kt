package me.slavita.construction.utils.extensions

import org.bukkit.inventory.PlayerInventory

object Extensions {
    fun PlayerInventory.swapItems(index1: Int, index2: Int) {
        if (!(0..35).contains(index1) || !(0..35).contains(index2)) throw IllegalArgumentException()

        val item1 = this.getItem(index1)
        this.setItem(index1, this.getItem(index2))
        this.setItem(index2, item1)
    }
}