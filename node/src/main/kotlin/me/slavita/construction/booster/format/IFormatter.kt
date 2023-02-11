package me.slavita.construction.booster.format

import me.slavita.construction.player.Data

interface IFormatter {
    fun format(value: Data): String
    fun format(value: Long): String
}
