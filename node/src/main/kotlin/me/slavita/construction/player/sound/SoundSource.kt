package me.slavita.construction.player.sound

import org.bukkit.entity.Player

interface SoundSource {
    fun play(player: Player, volume: Float, pitch: Float)
}