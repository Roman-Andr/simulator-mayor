package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.clientapi.JavaMod.loadTextureFromJar
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.Parent
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*

inline fun checkbox(initializer: CheckboxElement.() -> Unit) = CheckboxElement().also(initializer)

class CheckboxElement : CarvedRectangle(), Parent {
    var palette = ColorPalette.BLUE
    var redPalette = ColorPalette.RED
    var active = false
    val check = rectangle {
        align = CENTER
        origin = CENTER
        color = WHITE
        size = V3(14.0, 14.0)
        textureLocation = loadTextureFromJar(clientApi, "check", "check", "cross.png")
    }
    val box = carved {
        +check
        carveSize = 4.0
        align = V3(0.25, 0.5)
        origin = CENTER
        size = V3(26.0, 26.0)
        color = redPalette.none
    }

    init {
        carveSize = 4.0
        size = V3(52.0, 26.0)
        color = redPalette.none.apply { alpha = 0.28 }
        addChild(box)

        onHover {
            animate(0.08, Easings.QUINT_OUT) {
                update()
            }
        }

        onMouseUp {
            active = !active
            animate(0.13) {
                box.align.x = if (!active) 0.25 else 0.75
                update()
            }
        }
    }

    fun update() {
        check.textureLocation = if (active) loadTextureFromJar(clientApi, "checkbox", "check", "check.png")
        else loadTextureFromJar(clientApi, "checkbox", "cross", "cross.png")
        if (hovered) {
            color = (if (active) palette.light else redPalette.light).apply { alpha = 0.28 }
            box.color = (if (active) palette.light else redPalette.light).apply { alpha = 1.0 }
        } else {
            color = (if (active) palette.middle else redPalette.none).apply { alpha = 0.28 }
            box.color = (if (active) palette.middle else redPalette.none).apply { alpha = 1.0 }
        }
    }
}