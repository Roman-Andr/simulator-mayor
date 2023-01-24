package me.slavita.construction.booster.format

import me.slavita.construction.player.Data

object EmptyFormatter : IFormatter {
    override fun format(value: Data) = ""
    override fun format(value: Long) = ""
}