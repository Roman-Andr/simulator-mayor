package me.slavita.construction.ui.format

import me.slavita.construction.player.Data

class ProjectsFormatter : IFormatter {
    override fun format(value: Data) = value.totalProjects.toString()
    override fun format(value: Long) = value.toString()
}
