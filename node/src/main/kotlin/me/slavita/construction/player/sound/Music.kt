package me.slavita.construction.player.sound

import org.bukkit.entity.Player

object Music {
    val storageUrl = "https://storage.c7x.dev/${System.getProperty("storage.user")}/construction/sound/";

    fun Player.playSound(sound: MusicSound) {
        sound.playSound(this)
    }
}