package me.slavita.construction.booster.format

import me.slavita.construction.player.Data

object ProjectsFormatter : IFormatter {
    override fun format(value: Data): String {
        return value.statistics.totalProjects.toString()
    }
}