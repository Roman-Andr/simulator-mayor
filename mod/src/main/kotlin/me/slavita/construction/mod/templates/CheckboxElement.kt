package me.slavita.construction.mod.templates

import dev.xdark.clientapi.resource.ResourceLocation
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.clientapi.JavaMod.loadTextureFromJar
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.Parent
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*

inline fun checkbox(initializer: CheckboxElement.() -> Unit) = CheckboxElement().also(initializer)

class CheckboxElement : CarvedRectangle(), Parent {
    var noneColor = Color(0, 0, 0, 1.0)
        set(value) {
            color = value.apply { alpha = 0.28 }
            box.color = value.apply { alpha = 1.0 }
            field = value
        }
    var activeColor = Color(0, 0, 0, 1.0)
    var hoveredColor = Color(0, 0, 0, 1.0)
    var active = false
    val check = rectangle {
        align = CENTER
        origin = CENTER
        textureLocation = ResourceLocation.of("check.png")
    }
    val box = carved {
        +check
        carveSize = 4.0
        align = V3(0.25, 0.5)
        origin = CENTER
        size = V3(26.0, 26.0)
        color = noneColor
    }

    init {
        carveSize = 4.0
        size = V3(52.0, 26.0)
        color = noneColor.apply { alpha = 0.28 }
        addChild(box)

        onHover {
            animate(0.08, Easings.QUINT_OUT) {
                box.color = if (hovered) hoveredColor else noneColor
            }
        }

        onMouseUp {
            animate(0.1) {
                box.align.x = if (active) 0.25 else 0.75
                box.color = if (active) noneColor else activeColor
            }
            active = !active
        }
    }
}