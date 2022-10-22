package me.slavita.construction.mod.templates

import dev.xdark.clientapi.event.lifecycle.GameLoop
import me.slavita.construction.mod.mod
import me.slavita.construction.mod.utils.ColorPalette
import org.lwjgl.input.Mouse
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseDown
import ru.cristalix.uiengine.utility.*
import kotlin.math.abs
import kotlin.math.roundToInt

inline fun slider(initializer: SliderElement.() -> Unit) = SliderElement().also(initializer)

class SliderElement: CarvedRectangle() {
    val progress: Double
        get() {
            return (progressBox.size.x / size.x * 10000).roundToInt() / 10000.0
        }
    private var prevSize = 0.0
    private var draggingStart = 0.0
    private val cursor = carved {
        carveSize = 4.0
        align = RIGHT
        origin = CENTER
        color = WHITE
        size = V3(14.0, 30.0)
        offset.x = 3.0
        onMouseDown {
            drag()
        }
        onHover {
            animate(0.08) {
                color = when {
                    hovered && !Mouse.isButtonDown(0) -> Color(175, 208, 255, 1.0)
                    !hovered && !Mouse.isButtonDown(0) -> WHITE
                    else -> ColorPalette.BLUE.light.apply { alpha = 1.0 }
                }
            }
        }
    }
    private val progressBox = carved {
        carveSize = 4.0
        color = ColorPalette.BLUE.none
        align = LEFT
        origin = LEFT
        size = V3(0.0, 12.0)
        +cursor
        onMouseDown {
            size.x = hoverPosition.x
            prevSize = size.x
            drag()
        }
    }
    private var parts = flex {
        align = LEFT
        origin = LEFT
        flexDirection = FlexDirection.RIGHT
        flexSpacing = (451.0 - (partsCount + 1)*8.0)/(partsCount+1)
    }
    var partsCount = 0
        set(value) {
            parts.children.clear()
            field = value
            updateParts()
            magnetizeCursor()
        }

    init {
        updateParts()
        +parts
        onMouseDown {
            progressBox.size.x = hoverPosition.x
            prevSize = progressBox.size.x
            drag()
        }
        mod.registerHandler<GameLoop> {
            if (draggingStart != 0.0 && !Mouse.isButtonDown(0)) {
                draggingStart = 0.0
                prevSize = progressBox.size.x
                animate (0.05){
                    cursor.color = when {
                        cursor.hovered -> Color(175, 208, 255, 1.0)
                        else -> WHITE
                    }
                    if (partsCount != 0) {
                        magnetizeCursor()
                    }
                }
            }
            if (draggingStart != 0.0) {
                val target = prevSize + (Mouse.getX() / clientApi.resolution().scaleFactor - draggingStart)
                progressBox.size.x = if (target < 0.0) 0.0 else if (target > 451.0) 451.0 else target
            }
        }
        +progressBox
        carveSize = 4.0
        size = V3(451.0, 12.0)
        color = ColorPalette.BLUE.middle.apply { alpha = 0.62 }
    }

    private fun magnetizeCursor() {
        if (partsCount == 0) return
        UIEngine.schedule(0.13) {
            if (partsCount == 0) return@schedule
            animate(0.02) {
                val element = parts.children.minBy { abs(progressBox.size.x - it.offset.x) }
                progressBox.size.x = element.offset.x
            }
        }
    }

    private fun updateParts() {
        parts.flexSpacing = (451.0 - (partsCount + 1)*8.0)/(partsCount+1)
        if (partsCount == 0) return
        parts + rectangle {
            size = V3(8.0, 12.0)
        }
        repeat(partsCount) {
            parts +rectangle {
                size = V3(8.0, 12.0)
                color = ColorPalette.BLUE.none.apply { alpha = 1.0 }
            }
        }
        parts + rectangle {
            size = V3(8.0, 12.0)
        }
    }

    fun drag() {
        draggingStart = (Mouse.getX() / clientApi.resolution().scaleFactor).toDouble()
        animate(0.08) {
            cursor.color = ColorPalette.BLUE.light.apply { alpha = 1.0 }
        }
    }
}