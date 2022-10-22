package me.slavita.construction.mod.bank

import me.slavita.construction.mod.bank.CreditTaking.unaryPlus
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

    init {
        this.size = UIEngine.overlayContext.size
        color = Color(0, 0, 0, 0.86)
        //+title
        val input = +input {
            context = this@CreditTaking
            align = CENTER
            origin = LEFT
            offset.x = 100.0
            placeholder = "Введите значение"
        }

        val slider = +slider {
            offset.y = 150.0
            align = CENTER
            origin = CENTER
        }
        val switch = +switch {
            align = CENTER
            origin = CENTER
            text = listOf(
                "1",
                "2",
                "3",
            )
            scaleFactor = 0.7
        }
        val dropdown = +dropdown {
            offset.y = -150.0
            align = CENTER
            origin = CENTER
        }
        +flex {
            align = CENTER
            origin = CENTER
            flexDirection = FlexDirection.RIGHT
            flexSpacing = 10.0
            offset.y = 50.0
            val btn1 = +button {
                origin = CENTER
                palette = ColorPalette.GREEN
                content = "Добавить"
                onButtonClick {
                    dropdown.entries = dropdown.entries.toMutableList().apply { this[switch.activeValue.toInt() - 1] = input.value }
                    input.value = ""
                }
            }
            val btn2 = +button {
                origin = CENTER
                palette = ColorPalette.BLUE
                content = "Добавить"
                onButtonClick {
                    slider.partsCount++
                }
            }
            val btn3 = +button {
                origin = CENTER
                palette = ColorPalette.RED
                content = "Очистить"
                onButtonClick {
                    slider.partsCount = 0
                }
            }
            +toggle {
                onChange {
                    btn1.disable = !btn1.disable
                }
            }
            +toggle {
                onChange {
                    btn2.disable = !btn2.disable
                }
            }
            +toggle {
                onChange {
                    btn3.disable = !btn3.disable
                }
            }
        }


        mod.registerChannel("bank:open") {
            input.value = ""
            open()
        }
    }
}