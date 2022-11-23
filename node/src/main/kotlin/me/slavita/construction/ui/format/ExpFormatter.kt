package me.slavita.construction.ui.format

import me.slavita.construction.player.Data

class ExpFormatter : IFormatter {
    override fun format(value: Data): String {
        return value.statistics.experience.toString()
    }
}