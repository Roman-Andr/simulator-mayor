package me.slavita.construction.utils

import me.func.mod.Anime
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.utils.MusicExtension.playSound
import org.bukkit.entity.Player
import org.bukkit.inventory.PlayerInventory
import ru.cristalix.core.formatting.Formatting.error
import ru.cristalix.core.formatting.Formatting.fine

object PlayerExtensions {
    fun PlayerInventory.swapItems(firstIndex: Int, secondIndex: Int) {
        val firstItem = getItem(firstIndex)
        setItem(firstIndex, getItem(secondIndex))
        setItem(secondIndex, firstItem)
    }

    fun Player.killboard(text: String) {
        Anime.killboardMessage(this, text)
    }

    fun Player.deny(text: String) {
        killboard(error(text))
        playSound(MusicSound.DENY)
        Glow.animate(this, 0.4, GlowColor.RED)
    }

    fun Player.accept(text: String) {
        killboard(fine(text))
        playSound(MusicSound.LEVEL_UP)
        Glow.animate(this, 0.4, GlowColor.GREEN)
    }
}