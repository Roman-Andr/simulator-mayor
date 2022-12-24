package me.slavita.construction.player.sound

import org.bukkit.Sound
import org.bukkit.entity.Player

class MinecraftSound(private val sound: Sound): SoundSource {
    override fun play(player: Player, volume: Float, pitch: Float) {
        player.playSound(player.location, sound, volume, pitch)
    }
}