package me.slavita.construction.ui.format

import me.slavita.construction.player.Data

class ExpFormatter : IFormatter {
    override fun format(value: Data) = value.experience.toString()
    override fun format(value: Long) = value.toString()
}
