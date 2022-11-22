package me.slavita.construction.mod.templates

import dev.xdark.clientapi.event.lifecycle.GameLoop
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.mod.mod
import me.slavita.construction.mod.utils.extensions.ColorExtensions.toColor
import me.slavita.construction.mod.utils.extensions.PositionExtensions.inBox
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.utility.*

inline fun info(initializer: InformationBox.() -> Unit) = InformationBox().also(initializer)

class InformationBox : CarvedRectangle() {
    private var title: TextElement = text {
        origin = TOP
        align = TOP
        offset.y += 4.0
        color = GlowColor.GREEN_LIGHT.toColor().apply { color.alpha = 0.0 }
        content = ""
        shadow = true
    }
    private var description: TextElement = text {
        origin = BOTTOM
        align = BOTTOM
        offset.y -= 4.0
        color = GlowColor.CIAN.toColor().apply { color.alpha = 0.0 }
        content = ""
    }
    var boxes = arrayOf<BoxData>()
    var turn = false
    init {
        size = V3(210.0, 40.0)
        align = BOTTOM
        origin = BOTTOM
        offset.y -= 65.0
        color = Color(0, 0, 0, 0.0)
        +title
        +description

        mod.registerHandler<GameLoop> {
            UIEngine.clientApi.minecraft().mouseOver.pos?.run {
                if (boxes.any { inBox(it.min, it.max) } && !turn) {
                    println("turn on")
                    title.content = boxes.find { inBox(it.min, it.max) }!!.title
                    turn = true
                    show()
                }
                if (!boxes.any { inBox(it.min, it.max) } && turn) {
                    println("turn off")
                    turn = false
                    hide()
                }
            }
        }
    }

    fun description(text: String) {
        description.content = text
    }

    private fun show() {
        animate(0.2) {
            title.color.alpha = 1.0
            description.color.alpha = 1.0
            color.alpha = 0.52
        }
    }

    fun hide() {
        animate(0.2) {
            title.color.alpha = 0.0
            description.color.alpha = 0.0
            color.alpha = 0.0
        }
    }
}