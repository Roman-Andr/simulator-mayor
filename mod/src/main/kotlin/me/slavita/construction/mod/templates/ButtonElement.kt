package me.slavita.construction.mod.templates

import ru.cristalix.clientapi.JavaMod
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.Parent
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.utility.*

inline fun button(initializer: ButtonElement.() -> Unit) = ButtonElement().also(initializer)

class ButtonElement : CarvedRectangle(), Parent {
    var noneColor = Color(0, 0, 0, 1.0)
    var hoveredColor = Color(0, 0, 0, 1.0)
    var content: String =  ""
        set(value) {
            text.content = value
            size = V3(clientApi.fontRenderer().getStringWidth(text.content).toDouble() + 10.0, 20.0)
            field = value
        }
    var text = text {
        align = CENTER
        origin = CENTER
    }

    init {
        carveSize = 2.0
        color = noneColor
        content = ""
        addChild(text)

        onHover {
            animate(0.08, Easings.QUINT_OUT) {
                color = if (hovered) hoveredColor else noneColor
                scale = V3(if (hovered) 1.1 else 1.0, if (hovered) 1.1 else 1.0, 1.0)
            }
        }
    }
}