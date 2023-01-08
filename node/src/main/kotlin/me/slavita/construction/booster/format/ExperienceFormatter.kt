package me.slavita.construction.booster.format

import me.slavita.construction.player.Data

object ExperienceFormatter : IFormatter {
    override fun format(value: Data): String {
        return value.statistics.experience.toString()
    }
}