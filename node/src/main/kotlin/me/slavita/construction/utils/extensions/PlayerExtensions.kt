package me.slavita.construction.utils.extensions

import org.bukkit.inventory.PlayerInventory

object PlayerExtensions {
    fun PlayerInventory.swapItems(firstIndex: Int, secondIndex: Int) {
        val firstItem = getItem(firstIndex)
        setItem(firstIndex, getItem(secondIndex))
        setItem(secondIndex, firstItem)
    }
}