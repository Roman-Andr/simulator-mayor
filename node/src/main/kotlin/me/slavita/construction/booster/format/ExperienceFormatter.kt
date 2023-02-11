package me.slavita.construction.booster.format

import me.slavita.construction.player.Data

object ExperienceFormatter : IFormatter {
    override fun format(value: Data) = value.experience.toString()
    override fun format(value: Long) = value.toString()
}
