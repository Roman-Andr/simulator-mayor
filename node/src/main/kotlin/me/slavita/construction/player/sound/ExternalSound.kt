package me.slavita.construction.player.sound

import me.func.sound.Category
import me.func.sound.Sound
import org.bukkit.entity.Player

class ExternalSound(private val sound: String) : SoundSource {
    override fun play(player: Player, volume: Float, pitch: Float) {
        Sound(Music.storageUrl + sound)
            .category(Category.MASTER)
            .pitch(volume)
            .volume(pitch)
            .location(player.location)
            .repeating(false)
            .send(player)
    }
}