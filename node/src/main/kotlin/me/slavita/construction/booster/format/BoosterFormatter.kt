package me.slavita.construction.booster.format

import me.slavita.construction.player.Data

object BoosterFormatter : IFormatter {
    override fun format(value: Data) = value.totalBoosters.toString()
    override fun format(value: Long) = value.toString()
}