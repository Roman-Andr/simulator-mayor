package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import me.slavita.construction.mod.utils.getWidth
import ru.cristalix.uiengine.element.RectangleElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*

inline fun switch(initializer: SwitchElement.() -> Unit) = SwitchElement().also(initializer)

class SwitchElement: RectangleElement() {
    private val variants = mutableListOf<RectangleElement>()
    var text = listOf("Пусто")
        set(value) {
            field = value
            redraw()
            click()
        }
    var scaleFactor = 1.0
        set(value) {
            field = value
            redraw()
            click()
        }
    val activeValue
        get() = text[activeElement]
    private var activeElement = 0
        set(value) {
            field = value
            click()
        }
    private var interval = 4.0
        set(value) {
            field = value
            redraw()
        }
    private val back = carved {
        carveSize = 2.0
        align = CENTER
        origin = CENTER
        color = ColorPalette.BLUE.middle
    }
    private val activeBox = carved {
        carveSize = 2.0
        align = LEFT
        origin = LEFT
        color = ColorPalette.BLUE.none
    }
    private val container = flex {
        align = CENTER
        origin = CENTER
        flexSpacing = interval
        flexDirection = FlexDirection.RIGHT
    }

    init {
        +back
        back + activeBox
        +container
        redraw()
        click()
    }

    private fun click() {
        animate(0.25, Easings.CUBIC_OUT) {
            activeBox.size.x = container.children[activeElement].size.x
            activeBox.offset.x = container.children[activeElement].offset.x
        }
    }

    private fun redraw() {
        container.children.clear()
        text.forEachIndexed { index, s ->
            val entry = rectangle {
                size = V3((getWidth(s) + 32.0)*scaleFactor, 38.0*scaleFactor)
                val title = +text {
                    align = CENTER
                    origin = CENTER
                    content = s
                    onMouseUp {
                        activeElement = index
                        color.alpha = 1.0
                    }
                }
                onHover {
                    title.color.alpha = if (hovered && activeElement != index) 0.62 else 1.0
                }
                onMouseUp {
                    activeElement = index
                    title.color.alpha = 1.0
                }
            }
            container + entry
            variants.add(entry)
            back.size = V3(text.sumOf { (getWidth(it) + 32.0)*scaleFactor } + (text.size - 1) * interval, 38.0*scaleFactor)
        }
        activeBox.size.y = 38.0*scaleFactor
    }
}