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
    SUCCESS1(ExternalSound("success-1.ogg"), 1F, 1F),
    SUCCESS2(ExternalSound("success-2.ogg"), 1F, 1F),
    SUCCESS3(ExternalSound("success-3.ogg"), 1F, 1F),
    PING(ExternalSound("ping.ogg"), 1F, 1F),
    DENY1(ExternalSound("deny-1.ogg"), 1F, 1F);

    fun playSound(player: Player) {
        sound.play(player, volume, pitch)
    }
}