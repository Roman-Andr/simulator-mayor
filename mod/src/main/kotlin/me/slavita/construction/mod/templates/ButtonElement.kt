package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.utility.*

inline fun button(initializer: ButtonElement.() -> Unit) = ButtonElement().also(initializer)

class ButtonElement : CarvedRectangle() {
    var palette = ColorPalette.BLUE
        set(value) {
            color = value.none
            field = value
        }
    var content: String =  ""
        set(value) {
            text.content = value
            animate(0.05) {
                size = V3(clientApi.fontRenderer().getStringWidth(text.content).toDouble() + 10.0, 20.0)
            }
            field = value
        }
    var text = text {
        align = CENTER
        origin = CENTER
    }

    init {
        origin = CENTER
        carveSize = 2.0
        color = palette.none
        content = ""
        addChild(text)

        onHover {
            animate(0.08, Easings.QUINT_OUT) {
                color = if (hovered) palette.light else palette.none
                scale = V3(if (hovered) 1.1 else 1.0, if (hovered) 1.1 else 1.0, 1.0)
            }
        }
    }
}