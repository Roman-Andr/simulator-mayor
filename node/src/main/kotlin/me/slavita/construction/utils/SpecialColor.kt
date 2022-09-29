package me.slavita.construction.utils

import me.func.protocol.data.color.RGB
import me.func.protocol.data.color.Tricolor

enum class SpecialColor(override var red: Int, override var blue: Int, override var green: Int): RGB {

    GREEN(43, 228, 92),
    RED(255, 48, 48),
    GOLD(246, 198, 41);

    override fun toRGB() = toRGB(red, green, blue)
}