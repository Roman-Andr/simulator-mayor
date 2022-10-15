package me.slavita.construction.mod

import org.lwjgl.input.Keyboard
import ru.cristalix.uiengine.element.TextElement

val textPattern = Regex("\\d")

inline fun input(initializer: InputElement.() -> Unit) = InputElement().also(initializer)

class InputElement : TextElement() {

    var value: String = ""
    var focused: Boolean = true
    var pattern: Regex = textPattern
    var placeholder: String = ""

    init {
        beforeTransform {
            color.alpha = if (value.isEmpty() && !focused) 0.5 else 1.0
            content = when {
                !focused && value.isEmpty() -> placeholder
                !focused -> value
                System.currentTimeMillis() % 1000 > 500 -> "${value}_"
                else -> "$value "
            }
        }
        shadow = true
    }

    fun handleInput(char: Char, keyCode: Int) {
        if (keyCode == Keyboard.KEY_BACK && value.isNotEmpty()) value = value.substring(0, value.length - 1)
        else {
            if (char.toString().matches(pattern)) {
                println("accept")
                value += char
            }
        }
    }

}