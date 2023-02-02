package me.slavita.construction.booster.format

import me.slavita.construction.player.Data
import me.slavita.construction.ui.Formatter.toMoney

object ReputationFormatter : IFormatter {
    override fun format(value: Data) = value.reputation.toMoney()
    override fun format(value: Long) = value.toMoney()
}
