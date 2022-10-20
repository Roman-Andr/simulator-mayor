package me.slavita.construction.mod.templates

import dev.xdark.clientapi.event.lifecycle.GameLoop
import me.slavita.construction.mod.mod
import me.slavita.construction.mod.utils.ColorPalette
import org.lwjgl.input.Mouse
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseDown
import ru.cristalix.uiengine.utility.*

inline fun slider(initializer: SliderElement.() -> Unit) = SliderElement().also(initializer)

class SliderElement: CarvedRectangle() {
    val progress: Double
        get() {
            return progressBox.size.x / size.x
        }
    private var prevSize = 0.0
    private var draggingStart = 0.0
    private val cursor = carved {
        carveSize = 4.0
        align = RIGHT
        origin = CENTER
        color = WHITE
        size = V3(14.0, 30.0)
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

    init {
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

    fun drag() {
        draggingStart = (Mouse.getX() / clientApi.resolution().scaleFactor).toDouble()
        animate(0.08) {
            cursor.color = ColorPalette.BLUE.light.apply { alpha = 1.0 }
        }
    }
}