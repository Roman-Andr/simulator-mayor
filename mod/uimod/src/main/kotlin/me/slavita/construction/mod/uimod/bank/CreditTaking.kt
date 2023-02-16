package me.slavita.construction.mod.uimod.bank

import io.netty.buffer.Unpooled
import me.slavita.construction.common.utils.BANK_OPEN_CHANNEL
import me.slavita.construction.common.utils.BANK_SUBMIT_CHANNEL
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.common.utils.NumberFormatter
import me.slavita.construction.mod.uimod.mod
import me.slavita.construction.mod.uimod.templates.button
import me.slavita.construction.mod.uimod.templates.slider
import me.slavita.construction.mod.uimod.utils.ColorPalette
import me.slavita.construction.mod.uimod.utils.doubleVec
import me.slavita.construction.mod.uimod.utils.sendPayload
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ContextGui
import ru.cristalix.uiengine.utility.BOTTOM
import ru.cristalix.uiengine.utility.BOTTOM_LEFT
import ru.cristalix.uiengine.utility.BOTTOM_RIGHT
import ru.cristalix.uiengine.utility.CENTER
import ru.cristalix.uiengine.utility.Color
import ru.cristalix.uiengine.utility.FlexDirection
import ru.cristalix.uiengine.utility.LEFT
import ru.cristalix.uiengine.utility.TOP
import ru.cristalix.uiengine.utility.V3
import ru.cristalix.uiengine.utility.carved
import ru.cristalix.uiengine.utility.flex
import ru.cristalix.uiengine.utility.text
import kotlin.math.pow

object CreditTaking : ContextGui(), IRegistrable {
    private val back = carved {
        align = CENTER
        origin = CENTER
        carveSize = 3.0
        size = V3(173.5, 91.5)
        color = Color(75, 75, 75, 0.28)
        opened = false
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
                if (digit == 0) return@onButtonClick
                val buffer = Unpooled.buffer().writeInt(slider.activeId).writeInt(digit)
                UIEngine.schedule(0.1) {
                    sendPayload(BANK_SUBMIT_CHANNEL, buffer)
                    close()
                    opened = false
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
            if (!opened) {
                opened = true
                open()
            }
        }
    }
}
