package me.slavita.construction.mod.uimod.templates

import me.slavita.construction.mod.uimod.utils.ColorPalette
import me.slavita.construction.mod.uimod.utils.doubleVec
import org.lwjgl.input.Mouse
import ru.cristalix.uiengine.element.RectangleElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.utility.CENTER
import ru.cristalix.uiengine.utility.Easings
import ru.cristalix.uiengine.utility.carved
import ru.cristalix.uiengine.utility.text

inline fun button(initializer: ButtonElement.() -> Unit) = ButtonElement().also(initializer)

class ButtonElement : RectangleElement() {
    var disable = false
        set(value) {
            back.color = (if (value) ColorPalette.NEUTRAL.none else back.color).apply { alpha = 1.0 }
            if (!value) updateColor()
            field = value
        }
    var palette = ColorPalette.BLUE
        set(value) {
            back.color = value.none
            field = value
        }
    var action = {}
    var clicked = false
    var scaling = true
    var targetWidth = 0.0
        set(value) {
            field = value
            back.size.x = targetWidth
            back.size.y = 19.0
        }
    var content: String = ""
        set(value) {
            text.content = value
            animate(0.05) {
                back.size.x = targetWidth
                back.size.y = 19.0
            }
            field = value
        }
    private var text = text {
        align = CENTER
        origin = CENTER
        scale = 1.01.doubleVec()
    }
    private val back = carved {
        align = CENTER
        origin = CENTER
        carveSize = 2.0
        onHover {
            if (disable) return@onHover
            animate(0.08, Easings.QUINT_OUT) {
                color = (if (hovered && !Mouse.isButtonDown(0)) palette.light else palette.none).apply { alpha = 1.0 }
                // if (scaling) scale = (if (hovered && !Mouse.isButtonDown(0)) 1.1 else 1.0).doubleVec()
            }
        }
        onMouseStateChange {
            if (disable) return@onMouseStateChange
            clicked = down
            animate(0.08) {
                if (down) {
                    color = palette.middle
                    if (scaling) scale = 0.9.doubleVec()
                } else {
                    action()
                    color =
                        (if (hovered && !Mouse.isButtonDown(0)) palette.light else palette.none).apply { alpha = 1.0 }
                    if (scaling) scale = (if (hovered && !Mouse.isButtonDown(0)) 1.1 else 1.0).doubleVec()
                }
            }
        }
    }

    init {
        +back
        +text
        beforeTransform {
            size = back.size
        }
        updateColor()
    }

    fun onButtonClick(targetAction: () -> Unit) {
        action = targetAction
    }

    private fun updateColor() {
        back.color = (if (hovered && !Mouse.isButtonDown(0)) palette.light else palette.none).apply { alpha = 1.0 }
        if (scaling) back.scale = (if (hovered && !Mouse.isButtonDown(0)) 1.1 else 1.0).doubleVec()
    }
}
