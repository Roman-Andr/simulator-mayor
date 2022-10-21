package me.slavita.construction.mod.bank

import me.slavita.construction.mod.mod
import me.slavita.construction.mod.templates.*
import me.slavita.construction.mod.utils.ColorPalette
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ContextGui
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*

object CreditTaking: ContextGui() {
    private val title = text {
        color = WHITE
        align = V3(0.5, 0.4)
        origin = CENTER
        content = "Кредит\nВведите необходимую сумму (Пример: 2.4 тыс)"
    }

    private val moneyInput = input {
        context = this@CreditTaking
        align = CENTER
        origin = CENTER
        placeholder = "Введите сумму"
        this@CreditTaking.onMouseUp {
            focused = false
        }
    }

    init {
        this.size = UIEngine.overlayContext.size
        color = Color(0, 0, 0, 0.86)
        +title

        val slider = +slider {
            offset.y = 150.0
            align = CENTER
            origin = CENTER
        }
        +switch {
            align = CENTER
            origin = CENTER
            text = listOf(
                "0",
                "1",
                "2",
                "3",
                "4",
            )
            scaleFactor = 0.7
            onSwitch {
                println(activeValue.toInt())
                slider.partsCount = activeValue.toInt()
            }
        }
        +button {
            align = CENTER
            origin = CENTER
            offset.y = 50.0
            palette = ColorPalette.GREEN
            beforeTransform {
                content = slider.progress.toString()
            }
            onButtonClick {
                slider.partsCount++
            }
        }
        +toggle {
            offset.y = 100.0
            align = CENTER
            origin = CENTER
            palette = ColorPalette.BLUE
        }


        mod.registerChannel("bank:open") {
            moneyInput.value = ""
            open()
        }
    }
}