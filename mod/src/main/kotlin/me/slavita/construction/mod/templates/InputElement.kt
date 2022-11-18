package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import me.slavita.construction.mod.utils.getWidth
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.ContextGui
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*
import kotlin.math.max

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
        set(value) {
            back.size.x = max(getWidth(placeholder), getWidth(value)) + 12.0
            field = value
        }
    var focused: Boolean = true
    var placeholder: String = ""
        set(value) {
            back.size.x = getWidth(value) + 12.0
            field = value
        }
    private var text = text {
        align = LEFT
        origin = LEFT
        offset.x = 6.0
        shadow = true
    }
    private val back = carved {
        align = CENTER
        origin = CENTER
        carveSize = 2.0
        size = V3(max(getWidth(placeholder), getWidth(value)) + 12.0, 19.0)
        +text
        color = updateColor()
        beforeTransform {
            text.content = when {
                !focused && value.isEmpty()             -> placeholder
                !focused                                -> value
                System.currentTimeMillis() % 1000 > 500 -> "${value}|"
                else                                    -> "$value "
            }
            if (Mouse.isButtonDown(0) && !hovered) focused = false
        }
        onMouseUp {
            focused = !focused
        }
        onHover {
            animate(0.08) {
                color = if (hovered) ColorPalette.BLUE.light.apply { alpha = 0.62 } else updateColor()
            }
        }
    }

    init {
        +back
        beforeTransform {
            size = back.size
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