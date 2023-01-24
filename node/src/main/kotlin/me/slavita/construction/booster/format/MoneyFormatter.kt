package me.slavita.construction.ui.format

import me.slavita.construction.booster.format.IFormatter
import me.slavita.construction.player.Data
import me.slavita.construction.ui.Formatter.toMoneyIcon

object MoneyFormatter : IFormatter {
    override fun format(value: Data) = value.money.toMoneyIcon()
    override fun format(value: Long) = value.toMoneyIcon()
}
