package me.slavita.construction.ui.format

import me.slavita.construction.player.Data

class ReputationFormatter : IFormatter {
    override fun format(value: Data) = value.reputation.toString()
    override fun format(value: Long) = value.toString()
}
