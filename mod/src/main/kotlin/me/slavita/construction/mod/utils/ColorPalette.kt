package me.slavita.construction.mod.utils

import me.func.protocol.data.color.GlowColor
import me.slavita.construction.mod.utils.extensions.ColorExtensions.toColor
import ru.cristalix.uiengine.utility.Color

enum class ColorPalette(
    val light: Color,
    val none: Color,
    val middle: Color,
    val dark: Color,
) {
    BLUE(
        GlowColor.BLUE_LIGHT.toColor(),
        GlowColor.BLUE.toColor(),
        GlowColor.BLUE_MIDDLE.toColor(),
        GlowColor.BLUE_DARK.toColor()
    ),
    RED(
        GlowColor.RED_LIGHT.toColor(),
        GlowColor.RED.toColor(),
        GlowColor.RED_MIDDLE.toColor(),
        GlowColor.RED_DARK.toColor()
    ),
    YELLOW(
        GlowColor.YELLOW_LIGHT.toColor(),
        GlowColor.YELLOW.toColor(),
        GlowColor.YELLOW_MIDDLE.toColor(),
        GlowColor.YELLOW_DARK.toColor()
    ),
    GREEN(
        GlowColor.GREEN_LIGHT.toColor(),
        GlowColor.GREEN.toColor(),
        GlowColor.GREEN_MIDDLE.toColor(),
        GlowColor.GREEN_DARK.toColor()
    ),
    ORANGE(
        GlowColor.ORANGE_LIGHT.toColor(),
        GlowColor.ORANGE.toColor(),
        GlowColor.ORANGE_MIDDLE.toColor(),
        GlowColor.ORANGE_DARK.toColor()
    ),
    PURPLE(
        GlowColor.PURPLE_LIGHT.toColor(),
        GlowColor.PURPLE.toColor(),
        GlowColor.PURPLE_MIDDLE.toColor(),
        GlowColor.PURPLE_DARK.toColor()
    ),
    PINK(
        GlowColor.PINK_LIGHT.toColor(),
        GlowColor.PINK.toColor(),
        GlowColor.PINK_MIDDLE.toColor(),
        GlowColor.PINK_DARK.toColor()
    ),
    CIAN(
        GlowColor.CIAN_LIGHT.toColor(),
        GlowColor.CIAN.toColor(),
        GlowColor.CIAN_MIDDLE.toColor(),
        GlowColor.CIAN_DARK.toColor()
    ),
    NEUTRAL(
        GlowColor.NEUTRAL_LIGHT.toColor(),
        GlowColor.NEUTRAL.toColor(),
        GlowColor.NEUTRAL_MIDDLE.toColor(),
        GlowColor.NEUTRAL_DARK.toColor()
    ),
}