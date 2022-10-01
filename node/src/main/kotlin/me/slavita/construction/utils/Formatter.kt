package me.slavita.construction.utils

import me.func.protocol.data.emoji.Emoji
import org.bukkit.ChatColor.*

object Formatter {
    fun Long.toMoney(): String {
        return NumberConverter.toMoney(this)
    }

    fun Long.toMoneyIcon(): String {
        return "${toMoney()} $WHITE ${Emoji.DOLLAR}"
    }
}