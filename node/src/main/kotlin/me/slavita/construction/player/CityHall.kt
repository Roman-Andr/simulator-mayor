package me.slavita.construction.player

import me.slavita.construction.utils.PlayerExtensions.accept
import org.bukkit.ChatColor.GOLD

class CityHall {
    var level: Int = 1
    val upgradePrice
        get() = level * 1000L
    val income
        get() = level * 1000L
    val nextIncome
        get() = (level + 1) * 1000L
}
