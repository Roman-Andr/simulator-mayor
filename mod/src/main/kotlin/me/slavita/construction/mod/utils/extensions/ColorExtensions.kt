package me.slavita.construction.mod.utils.extensions

import me.func.protocol.data.color.GlowColor
import ru.cristalix.uiengine.utility.Color

object ColorExtensions {
    fun GlowColor.toColor(): Color {
        return Color(red, green, blue, 1.0)
    }
}