package me.slavita.construction.mod.bank

import io.netty.buffer.Unpooled
import me.slavita.construction.common.utils.*
import me.slavita.construction.mod.mod
import me.slavita.construction.mod.templates.button
import me.slavita.construction.mod.templates.slider
import me.slavita.construction.mod.utils.ColorPalette
import me.slavita.construction.mod.utils.doubleVec
import me.slavita.construction.mod.utils.sendPayload
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ContextGui
import ru.cristalix.uiengine.utility.*
import kotlin.math.pow

object CreditTaking : ContextGui(), IRegistrable {
    private val back = carved {
        align = CENTER
        origin = CENTER
        carveSize = 3.0
        size = V3(173.5, 91.5)
        color = Color(75, 75, 75, 0.28)
        +carved {
            carveSize = 3.0
            align = CENTER
            origin = CENTER
            size = V3(173.5, 28.5)
            color = ColorPalette.BLUE.none.apply { alpha = 0.28 }
        }
        val slider = +slider {
            align = LEFT
            offset.x = 8.0
            partsCount = 9
            targetWidth = 157.5
        }
        +text {
            align = TOP
            origin = TOP
            offset.y = 11.0
            content = "Выберите сумму"
            scale = 1.1.doubleVec()
            beforeTransform {
                content =
                    if (slider.activeId != 0) NumberFormatter.toMoneyFormat((slider.activeId * 10.0.pow(digit)).toLong()) else "Выберите сумму"
            }
        }
        +text {
            align = TOP
            origin = BOTTOM
            offset.y = -30.0
            scale = 2.0.doubleVec()
            content = "БАНК"
        }

        +button {
            align = BOTTOM_RIGHT
            origin = BOTTOM_RIGHT
            offset.x = -7.0
            offset.y = -7.0
            targetWidth = 53.0
            content = "Отмена"
            palette = ColorPalette.RED
            scaling = false

            onButtonClick {
                close()
            }
        }
        +button {
            align = BOTTOM_LEFT
            origin = BOTTOM_LEFT
            offset.x = 7.0
            offset.y = -7.0
            targetWidth = 103.5
            content = "Взять кредит"
            palette = ColorPalette.BLUE
            scaling = false

            onButtonClick {
                val buffer = Unpooled.buffer().writeInt(slider.activeId).writeInt(digit)
                UIEngine.schedule(0.1) {
                    sendPayload(BANK_SUBMIT_CHANNEL, buffer)
                    close()
                }
            }
        }

        +flex {
            flexDirection = FlexDirection.RIGHT
            flexSpacing = 5.0
            align = BOTTOM
            origin = BOTTOM
            offset.y = -7.0
        }
    }

    private var digit = 0

    override fun register() {
        this.size = UIEngine.overlayContext.size
        color = Color(0, 0, 0, 0.86)

        +back

        mod.registerChannel(BANK_OPEN_CHANNEL) {
            digit = readInt()
            open()
        }
    }
}