package me.slavita.construction.booster.format

import me.slavita.construction.player.Data
import me.slavita.construction.ui.Formatter.toReputation

object ReputationFormatter : IFormatter {
    override fun format(value: Data) = value.reputation.toReputation()
    override fun format(value: Long) = value.toReputation()
}
