package me.slavita.construction.utils.extensions

import me.func.mod.Anime
import org.bukkit.entity.Player
import org.bukkit.inventory.PlayerInventory

object PlayerExtensions {
    fun PlayerInventory.swapItems(firstIndex: Int, secondIndex: Int) {
        val firstItem = getItem(firstIndex)
        setItem(firstIndex, getItem(secondIndex))
        setItem(secondIndex, firstItem)
    }

    fun Player.killboard(text: String) {
        Anime.killboardMessage(this, text)
    }
}