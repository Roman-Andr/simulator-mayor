package me.slavita.construction.ui.format

import me.slavita.construction.player.Data

interface IFormatter {
    fun format(value: Data): String
}