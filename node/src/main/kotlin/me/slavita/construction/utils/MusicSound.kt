package me.slavita.construction.utils

import org.bukkit.Sound

enum class MusicSound(
    val sound: Sound,
    val volume: Float,
    val pitch: Float,
) {
    UI_CLICK(Sound.UI_BUTTON_CLICK, 1F, 1F),
    LEVEL_UP(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F),
    DENY(Sound.ENTITY_VILLAGER_NO, 1F, 1F),
}