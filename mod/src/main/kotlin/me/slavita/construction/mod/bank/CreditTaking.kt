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
    private val back = carved {
        align = CENTER
        origin = CENTER
        carveSize = 3.0
        size = V3(173.5, 91.5)
        color = Color(75, 75, 75, 0.28)
        +slider {
            align = CENTER
            origin = CENTER
            partsCount = 7
            targetWidth = 173.5
        }

        +flex {
            flexDirection = FlexDirection.RIGHT
            flexSpacing = 5.0
            align = BOTTOM
            origin = BOTTOM
            offset.y = -7.0
            +button {
                targetWidth = 53.0
                content = "Взять кредит"
                palette = ColorPalette.BLUE
                scaling = false
            }
            +button {
                targetWidth = 103.5
                content = "Отмена"
                palette = ColorPalette.RED
                scaling = false
            }
        }
    }

    init {
        this.size = UIEngine.overlayContext.size
        color = Color(0, 0, 0, 0.86)

        +back


        mod.registerChannel("bank:open") {
            open()
        }
    }
}