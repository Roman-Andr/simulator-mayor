package me.slavita.construction.utils

import me.func.protocol.data.color.RGB

enum class SpecialColor(override var red: Int, override var green: Int, override var blue: Int,): RGB {

    GREEN(43, 228, 92),
    RED(255, 48, 48),
    GOLD(246, 198, 41);

    override fun toRGB() = toRGB(red, green, blue)
}