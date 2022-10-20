package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import ru.cristalix.clientapi.JavaMod.loadTextureFromJar
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*

inline fun toggle(initializer: ToggleElement.() -> Unit) = ToggleElement().also(initializer)

class ToggleElement : CarvedRectangle() {
    var palette = ColorPalette.BLUE
    var status = false
    var active = true
        set(value) {
            field = value
            update()
        }
    var action = {}
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
        color = ColorPalette.RED.none
    }

    init {
        carveSize = 4.0
        size = V3(52.0, 26.0)
        addChild(box)
        update()

        onHover {
            if (!active) return@onHover
            animate(0.08, Easings.QUINT_OUT) {
                update()
            }
        }

        onMouseUp {
            if (!active) return@onMouseUp
            status = !status
            animate(0.13) {
                box.align.x = if (!status) 0.25 else 0.75
                update()
                action()
            }
        }
    }

    fun update() {
        check.textureLocation = if (status) loadTextureFromJar(clientApi, "checkbox", "check", "check.png")
        else loadTextureFromJar(clientApi, "checkbox", "cross", "cross.png")
        if (!active) {
            color = ColorPalette.NEUTRAL.none.apply { alpha = 0.28 }
            box.color = ColorPalette.NEUTRAL.none.apply { alpha = 1.0 }
            return
        }
        if (hovered) {
            (if (status) palette.light else ColorPalette.RED.light).run {
                color = this.apply { alpha = 0.28 }
                box.color = this.apply { alpha = 0.62 }
            }
        } else {
            (if (status) palette.middle else ColorPalette.RED.none).run {
                color = this.apply { alpha = 0.28 }
                box.color = this.apply { alpha = 1.0 }
            }
        }
    }

    fun onChange(value: () -> Unit) {
        action = value
    }
}