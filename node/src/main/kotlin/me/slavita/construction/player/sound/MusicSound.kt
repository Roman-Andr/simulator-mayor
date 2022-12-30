package me.slavita.construction.player.sound

import org.bukkit.Sound
import org.bukkit.entity.Player

enum class MusicSound(
    private val sound: SoundSource,
    private val volume: Float,
    private val pitch: Float,
) {
    UI_CLICK(MinecraftSound(Sound.UI_BUTTON_CLICK), 1F, 1F),
    LEVEL_UP(MinecraftSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP), 1F, 1F),
    DENY(MinecraftSound(Sound.ENTITY_VILLAGER_NO), 1F, 1F),
    HINT(ExternalSound("hint.mp3"), 1F, 1F),
    SUCCESS3(ExternalSound("success-3.mp3"), 1F, 1F);

    fun playSound(player: Player) {
        sound.play(player, volume, pitch)
    }
}