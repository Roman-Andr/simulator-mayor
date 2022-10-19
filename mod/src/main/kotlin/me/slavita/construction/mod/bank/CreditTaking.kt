package me.slavita.construction.mod.bank

import io.netty.buffer.Unpooled
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.mod.SpecialColor
import me.slavita.construction.mod.mod
import me.slavita.construction.mod.templates.button
import me.slavita.construction.mod.templates.checkbox
import me.slavita.construction.mod.templates.input
import me.slavita.construction.mod.utils.extensions.ColorExtensions.toColor
import org.lwjgl.input.Keyboard
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.ContextGui
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*
import kotlin.math.pow

object CreditTaking: ContextGui() {
    private fun createButton(position: V3, title: String, normalColor: Color, hoveredColor: Color, click: () -> Unit, key: Int): CarvedRectangle {
        return carved {
            carveSize = 2.0
            align = position
            origin = CENTER
            size = V3(clientApi.fontRenderer().getStringWidth(title).toDouble() + 10.0, 20.0)
            color = normalColor
            onHover {
                animate(0.08, Easings.QUINT_OUT) {
                    color = if (hovered) hoveredColor else normalColor
                    scale = V3(if (hovered) 1.1 else 1.0, if (hovered) 1.1 else 1.0, 1.0)
                }
            }
            onMouseUp {
                click()
            }
            onKeyTyped { c, i ->
                if (i == key) click()
            }
            +text {
                align = CENTER
                origin = CENTER
                color = WHITE
                content = title
                shadow = true
            }
        }
    }

    private val title = text {
        color = WHITE
        align = V3(0.5, 0.4)
        origin = CENTER
        content = "Кредит\nВведите необходимую сумму (Пример: 2.4 тыс)"
    }

    private val moneyInput = input {
        onKeyTyped { c, i ->
            if (i == Keyboard.KEY_RETURN) return@onKeyTyped
            handleInput(c, i)
        }
        align = CENTER
        origin = CENTER
        placeholder = "Введите сумму"
    }

    init {
        this.size = UIEngine.overlayContext.size
        color = Color(0, 0, 0, 0.86)
        +title
        +moneyInput
        +createButton(
            V3(0.5, 0.6),
            "Подтвердить [ENTER]",
            SpecialColor.GREEN_MIDDLE.toColor(),
            SpecialColor.GREEN_LIGHT.toColor(),
            action@ {
                val values = listOf(
                    Pair("тыс", 10.0.pow(3)),
                    Pair("млн", 10.0.pow(6)),
                    Pair("млрд", 10.0.pow(9)),
                    Pair("трлн", 10.0.pow(12)),
                    Pair("квдрлн", 10.0.pow(15)),
                    Pair("квнтлн", 10.0.pow(18)),
                    Pair("скcтлн", 10.0.pow(21)),
                    Pair("сптлн", 10.0.pow(24)),
                )
                if (moneyInput.value.split(" ").size > 1 && moneyInput.value.split(" ")[0].toDoubleOrNull() != null) {
                    val target = values.find { it.first == moneyInput.value.split(" ")[1] }
                    clientApi.clientConnection().sendPayload("bank:submit", Unpooled.buffer().apply { writeDouble(moneyInput.value.split(" ")[0].toDouble() * target!!.second) })
                    close()
                }
                println("error")
            },
            Keyboard.KEY_RETURN
        )

        +createButton(
            V3(0.5, 0.7),
            "Выйти [ESC]",
            SpecialColor.RED_MIDDLE.toColor(),
            SpecialColor.RED_LIGHT.toColor(),
            { close() },
            Keyboard.KEY_ESCAPE
        )

        +checkbox {
            align = CENTER
            origin = CENTER

            noneColor = GlowColor.GREEN.toColor()
            hoveredColor = GlowColor.GREEN_LIGHT.toColor()
            activeColor = GlowColor.GREEN_MIDDLE.toColor()
        }


        mod.registerChannel("bank:open") {
            moneyInput.value = ""
            open()
        }
    }
}