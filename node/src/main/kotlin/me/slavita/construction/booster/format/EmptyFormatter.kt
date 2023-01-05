package me.slavita.construction.booster.format

import me.slavita.construction.player.Data

object EmptyFormatter : IFormatter {
    override fun format(value: Data): String {
        return ""
    }
}