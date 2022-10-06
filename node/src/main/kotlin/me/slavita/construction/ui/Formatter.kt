package me.slavita.construction.ui

import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.utils.NumberConverter
import org.bukkit.ChatColor.WHITE

object Formatter {
    fun Long.toMoney(): String {
        return NumberConverter.toMoney(this)
    }

    fun Long.toMoneyIcon(): String {
        return "${toMoney()} $WHITE${Emoji.DOLLAR}"
    }
}