package me.slavita.construction.ui

import me.func.protocol.data.emoji.Emoji
import me.func.stronghold.util.withBoosters
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.common.utils.NumberFormatter
import me.slavita.construction.common.utils.TimeFormatter
import org.bukkit.ChatColor.WHITE

object Formatter {
    fun Long.toMoney(): String {
        return NumberFormatter.toMoneyFormat(this)
    }

    fun Long.toMoneyIcon(): String {
        return "${toMoney()} $WHITE${Emoji.DOLLAR}"
    }

    fun Long.toLevel(): String {
        return "$this $WHITE${Emoji.UP}"
    }

    fun Long.toExp(): String {
        return "${toMoney()} ${Emoji.EXP}"
    }

    fun Long.toReputation(): String {
        return "${toMoney()} $WHITE${Emoji.RUBY}"
    }

    fun Long.toTimeIcon(): String {
        return "${toTime()} $WHITE${Emoji.TIME}"
    }

    fun Long.toTime(): String {
        return TimeFormatter.toTimeFormat(this)
    }

    fun Long.applyBoosters(vararg boosters: BoosterType): Long {
        return this.withBoosters(*boosters.map { it.label }.toTypedArray()).toLong()
    }

    fun Float.applyBoosters(vararg boosters: BoosterType): Float {
        return this.withBoosters(*boosters.map { it.label }.toTypedArray()).toFloat()
    }
}