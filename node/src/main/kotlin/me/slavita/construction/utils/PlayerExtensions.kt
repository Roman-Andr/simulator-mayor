package me.slavita.construction.utils

import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.player.sound.Music.playSound
import me.slavita.construction.player.sound.MusicSound
import org.bukkit.entity.Player
import ru.cristalix.core.formatting.Formatting.error
import ru.cristalix.core.formatting.Formatting.fine

object PlayerExtensions {
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