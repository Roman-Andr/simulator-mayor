package me.slavita.construction.ui.format

import me.slavita.construction.player.Data

class ProjectsFormatter : IFormatter {
    override fun format(value: Data): String {
        return value.statistics.totalProjects.toString()
    }
}