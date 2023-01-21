package me.slavita.construction.ui.format

import me.slavita.construction.player.Data

class ReputationFormatter : IFormatter {
    override fun format(value: Data): String {
        return value.reputation.toString()
    }
}
