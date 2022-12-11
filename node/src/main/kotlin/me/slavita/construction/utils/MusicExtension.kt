package me.slavita.construction.utils

import org.bukkit.entity.Player

object MusicExtension {
    fun Player.playSound(sound: MusicSound) {
        playSound(location, sound.sound, sound.volume, sound.pitch)
    }
}