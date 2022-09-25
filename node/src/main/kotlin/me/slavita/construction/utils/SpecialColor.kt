package me.slavita.construction.utils

import me.func.protocol.data.color.Tricolor

enum class SpecialColor(
    val red: Int,
    val green: Int,
    val blue: Int,
    val alpha: Double,
) {
    GREEN(43, 228, 92, 65.0),
    RED(255, 48, 48, 65.0),
    GOLD(246, 198, 41, 65.0);

    fun toRGB(): Tricolor {
        return Tricolor(red, green, blue)
    }
}