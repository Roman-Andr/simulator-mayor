package me.slavita.construction.ui.format

import me.slavita.construction.booster.format.IFormatter
import me.slavita.construction.player.Data

object ProjectsFormatter : IFormatter {
    override fun format(value: Data) = value.totalProjects.toString()
    override fun format(value: Long) = value.toString()
}
