package me.slavita.construction.mod.bank

import me.slavita.construction.mod.KeysManager
import me.slavita.construction.mod.input
import org.lwjgl.input.Keyboard
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ContextGui
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*

object CreditTaking: ContextGui() {
    val back = rectangle {
        size = UIEngine.overlayContext.size
        color = Color(0, 0, 0, 0.8)
        align = CENTER
        origin = CENTER
    }

    val moneyInputBox = rectangle {
        align = CENTER
        origin = CENTER
        color = Color(0, 0, 0, 0.83)
        size = V3(100.0, 24.0)
    }

    val moneyInput = moneyInputBox + input {
        align = CENTER
        origin = CENTER
        placeholder = "Введите сумму"
    }

    val keyExit = carved {
        carveSize = 2.0
        align = V3(0.5, 0.7)
        origin = CENTER
        size = V3(76.0, 20.0)
        val normalColor = Color(160, 29, 40, 0.83)
        val hoveredColor = Color(231, 61, 75, 0.83)
        color = normalColor
        onHover {
            animate(0.08, Easings.QUINT_OUT) {
                color = if (hovered) hoveredColor else normalColor
                scale = V3(if (hovered) 1.1 else 1.0, if (hovered) 1.1 else 1.0, 1.0)
            }
        }
        onMouseUp {
            close()
        }
        +text {
            align = CENTER
            origin = CENTER
            color = WHITE
            scale = V3(0.9, 0.9, 0.9)
            content = "Выйти [ ESC ]"
            shadow = true
        }
    }

    init {
        +back
        +moneyInput
        +keyExit
        onKeyTyped { c, i ->
            moneyInput.handleInput(c, i)
        }

        KeysManager.registerKey(Keyboard.KEY_B) {
            open()
        }
    }
}