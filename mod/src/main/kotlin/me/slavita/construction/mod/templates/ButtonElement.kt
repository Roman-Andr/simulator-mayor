package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import me.slavita.construction.mod.utils.doubleVec
import org.lwjgl.input.Mouse
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.utility.CENTER
import ru.cristalix.uiengine.utility.Easings
import ru.cristalix.uiengine.utility.V3
import ru.cristalix.uiengine.utility.text

inline fun button(initializer: ButtonElement.() -> Unit) = ButtonElement().also(initializer)

class ButtonElement : CarvedRectangle() {
    var disable = false
        set(value) {
            color =  if (value) ColorPalette.NEUTRAL.none else color
            field = value
        }
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
    private var text = text {
        align = CENTER
        origin = CENTER
    }
    var action = {}
    var clicked = false

    init {
        carveSize = 2.0
        color = palette.none
        +text

        onHover {
            if (disable) return@onHover
            animate(0.08, Easings.QUINT_OUT) { updateColor() }
        }

        onMouseStateChange {
            clicked = down
            animate(0.08) {
                if (down) {
                    color = palette.middle
                    scale = 0.9.doubleVec()
                } else  {
                    action()
                    updateColor()
                }
            }
        }
    }

    fun onButtonClick(targetAction: () -> Unit) {
        action = targetAction
    }

    private fun updateColor() {
        color = if (hovered && !Mouse.isButtonDown(0)) palette.light else palette.none
        scale = (if (hovered && !Mouse.isButtonDown(0)) 1.1 else 1.0).doubleVec()
    }
}