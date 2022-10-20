package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import org.lwjgl.input.Keyboard
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.ContextGui
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.Color
import ru.cristalix.uiengine.utility.LEFT
import ru.cristalix.uiengine.utility.V3
import ru.cristalix.uiengine.utility.text

inline fun input(initializer: InputElement.() -> Unit) = InputElement().also(initializer)
val textLinePattern = Regex("[A-ZА-Яа-яЁёa-z0-9\\s.]")

class InputElement : CarvedRectangle() {
    var context = ContextGui()
        set(value) {
            value.onKeyTyped { c, i ->
                handleInput(c, i)
            }
            field = value
        }
    var value: String = ""
    var focused: Boolean = true
    var placeholder: String = ""
    var width = 180.0
        set(value) {
            size.x = value
            field = value
        }
    private var text = text {
        align = LEFT
        origin = LEFT
        offset.x = 5.0
        shadow = true
    }

    init {
        size = V3(width, 38.0)
        +text
        color = updateColor()
        beforeTransform {
            text.content = when {
                !focused && value.isEmpty() -> placeholder
                !focused -> value
                System.currentTimeMillis() % 1000 > 500 -> "${value}|"
                else -> "$value "
            }
        }
        onMouseUp {
            focused = !focused
        }
        onHover {
            animate(0.08) {
                color = if (hovered) ColorPalette.BLUE.light else updateColor()
            }
        }
    }

    private fun handleInput(char: Char, keyCode: Int) {
        if (!focused) return
        if (keyCode == Keyboard.KEY_BACK && value.isNotEmpty()) value = value.substring(0, value.length - 1)
        else {
            if (char.toString().matches(textLinePattern))
                value += char
        }
    }

    private fun updateColor(): Color {
        return (if (focused) ColorPalette.BLUE.none else ColorPalette.BLUE.middle).apply { alpha = 0.62 }
    }
}