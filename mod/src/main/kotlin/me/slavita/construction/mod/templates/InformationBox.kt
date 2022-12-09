package me.slavita.construction.mod.templates

import me.func.protocol.data.color.GlowColor
import me.slavita.construction.mod.utils.extensions.ColorExtensions.toColor
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
        color = GlowColor.YELLOW.toColor().apply { color.alpha = 0.0 }
        content = ""
    }
    var boxes = arrayOf<BoxData>()
    var turn = false

    init {
        size = V3(210.0, 40.0)
        align = BOTTOM
        origin = BOTTOM
        offset.y -= 75.0
        color = Color(0, 0, 0, 0.0)
        +title
        +description
    }

    fun description(text: String) {
        description.content = text
    }

    fun title(text: String) {
        title.content = text
    }

    fun show() {
        animate(0.1) {
            title.color.alpha = 1.0
            description.color.alpha = 1.0
            color.alpha = 0.52
        }
    }

    fun hide() {
        animate(0.1) {
            title.color.alpha = 0.0
            description.color.alpha = 0.0
            color.alpha = 0.0
        }
    }
}