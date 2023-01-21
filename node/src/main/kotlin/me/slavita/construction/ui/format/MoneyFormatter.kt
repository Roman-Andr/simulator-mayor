package me.slavita.construction.ui.format

import me.slavita.construction.player.Data
import me.slavita.construction.ui.Formatter.toMoneyIcon

class MoneyFormatter : IFormatter {
    override fun format(value: Data): String {
        return value.money.toMoneyIcon().toString()
    }
}
