package me.slavita.construction.mod

import ru.cristalix.uiengine.utility.Color

enum class SpecialColor(
	val red: Int,
	val green: Int,
	val blue: Int,
	val alpha: Double,
) {
	GREEN(43, 228, 92, 65.0),
	GREEN_LIGHT(73, 223, 115, 0.8),
	GREEN_MIDDLE(34, 174, 73, 0.8),
	RED(255, 48, 48, 65.0),
	RED_LIGHT(231, 61, 75, 0.8),
	RED_MIDDLE(169, 25, 37, 0.8),
	ORANGE_LIGHT(255, 157, 66, 0.8),
	ORANGE(224, 118, 20, 0.8),
	GOLD(246, 198, 41, 65.0);

	fun toColor(): Color {
		return Color(red, green, blue, alpha)
	}
}