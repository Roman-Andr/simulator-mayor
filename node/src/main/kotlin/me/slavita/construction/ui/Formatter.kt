package me.slavita.construction.ui

import me.func.protocol.data.emoji.Emoji
import me.func.stronghold.util.withBoosters
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.common.utils.NumberFormatter
import me.slavita.construction.common.utils.TimeFormatter
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.WHITE

object Formatter {
    const val moneyIcon = Emoji.COIN
    const val incomeIcon = Emoji.DOLLAR
    const val donateIcon = Emoji.DONATE

    fun <T : Number> T.toMoney(): String {
        return NumberFormatter.toMoneyFormat(this.toLong())
    }

    fun <T : Number> T.toMoneyIcon(): String {
        return "$GOLD${toMoney()} $WHITE$moneyIcon"
    }

    fun <T : Number> T.toIncomeIcon(): String {
        return "${toMoney()} $WHITE$incomeIcon"
    }

    fun <T : Number> T.toLevel(): String {
        return "${this.toInt()} $WHITE${Emoji.UP}"
    }

    fun <T : Number> T.toExp(): String {
        return "${toMoney()} ${Emoji.EXP}"
    }

    fun <T : Number> T.toReputation(): String {
        return "${toMoney()} $WHITE${Emoji.RUBY}"
    }

    fun <T : Number> T.toTimeIcon(): String {
        return "${toTime()} $WHITE${Emoji.TIME}"
    }

    fun <T : Number> T.toTime(): String {
        return TimeFormatter.toTimeFormat(this.toLong())
    }

    fun <T : Number> T.ticksToTime(): String {
        return TimeFormatter.toTimeFormat(this.toLong() * 50)
    }

    fun <T : Number> T.applyBoosters(vararg boosters: BoosterType): Long {
        return this.withBoosters(*boosters.map { it.label }.toTypedArray()).toLong()
    }

    fun <T : Number> T.toCriMoney(): String {
        return "${AQUA}${this.toInt()} $donateIcon"
    }
}
