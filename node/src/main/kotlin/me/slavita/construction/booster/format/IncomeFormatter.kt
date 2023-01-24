package me.slavita.construction.booster.format

import me.slavita.construction.player.Data
import me.slavita.construction.ui.Formatter.toIncomeIcon

object IncomeFormatter : IFormatter {
    override fun format(value: Data) = value.lastIncome.toIncomeIcon()
    override fun format(value: Long) = value.toIncomeIcon()
}