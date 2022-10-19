package me.slavita.construction.mod.templates

import org.lwjgl.input.Keyboard
import ru.cristalix.uiengine.element.TextElement

inline fun input(initializer: InputElement.() -> Unit) = InputElement().also(initializer)
val textLinePattern = Regex("[A-ZА-Яа-яЁёa-z0-9\\s.]")

class InputElement : TextElement() {

    var value: String = ""
    var focused: Boolean = true
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
            if (char.toString().matches(textLinePattern))
                value += char
        }
    }

}