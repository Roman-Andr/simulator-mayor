package me.slavita.construction.booster.format

import me.slavita.construction.player.Data
import me.slavita.construction.ui.Formatter.toMoneyIcon

object MoneyFormatter : IFormatter {
    override fun format(value: Data): String {
        return value.statistics.money.toMoneyIcon()
    }
}