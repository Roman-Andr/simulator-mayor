package me.slavita.construction.player.sound

import me.slavita.construction.utils.STORAGE_URL
import org.bukkit.entity.Player

object Music {
    val storageUrl = "${STORAGE_URL}/sound/"

    fun Player.playSound(sound: MusicSound) {
        sound.playSound(this)
    }
}