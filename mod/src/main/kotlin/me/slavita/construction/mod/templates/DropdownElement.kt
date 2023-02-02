package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import me.slavita.construction.mod.utils.doubleVec
import me.slavita.construction.mod.utils.getWidth
import ru.cristalix.clientapi.JavaMod
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.AbstractElement
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.Parent
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.BOTTOM
import ru.cristalix.uiengine.utility.CENTER
import ru.cristalix.uiengine.utility.Easings
import ru.cristalix.uiengine.utility.FlexDirection
import ru.cristalix.uiengine.utility.LEFT
import ru.cristalix.uiengine.utility.RIGHT
import ru.cristalix.uiengine.utility.TOP
import ru.cristalix.uiengine.utility.V3
import ru.cristalix.uiengine.utility.WHITE
import ru.cristalix.uiengine.utility.carved
import ru.cristalix.uiengine.utility.flex
import ru.cristalix.uiengine.utility.rectangle
import ru.cristalix.uiengine.utility.text
import kotlin.math.max

inline fun dropdown(initializer: DropdownElement.() -> Unit) = DropdownElement().also(initializer)

class DropdownElement : CarvedRectangle() {
    var disable = false
        set(value) {
            color = if (value) ColorPalette.NEUTRAL.none.apply { alpha = 1.0 } else color
            field = value
        }
    var opened = false
    var placeholder = "Выберите"
        set(value) {
            title.content = value
            field = value
        }
    var entries = listOf(
        "Элемент 1",
        "Элемент 2",
        "Элемент 3",
    )
        set(value) {
            field = value
            initElements()
        }
    private var activeValueIndex = 0
    private val title = text {
        align = LEFT
        origin = LEFT
        offset.x = 6.0
        content = placeholder
    }
    private val arrow = rectangle {
        textureLocation = JavaMod.loadTextureFromJar(clientApi, "dropdown", "arrow", "arrow.png")
        align = RIGHT
        origin = CENTER
        offset.x = -10.0
        color = WHITE
        size = 8.0.doubleVec()
    }
    private val back = carved {
        carveSize = 2.0
        align = TOP
        origin = TOP
        color = ColorPalette.BLUE.none.apply { alpha = 0.0 }
        size = V3(89.5, 19.0)
    }
    private val elements = flex {
        flexDirection = FlexDirection.DOWN
        flexSpacing = 0.0
        align = BOTTOM
        origin = TOP
    }
    private val topElement = carved {
        align = CENTER
        origin = CENTER
        carveSize = 2.0
        color = ColorPalette.BLUE.middle
        size = V3(89.5, 19.0)
        +back
        +title
        +arrow
        +elements

        onHover {
            animate(0.08, Easings.QUINT_OUT) {
                color = (if (hovered) ColorPalette.BLUE.none else ColorPalette.BLUE.middle).apply { alpha = 1.0 }
            }
        }
        onMouseUp {
            opened = !opened
            animateAlpha()
            animateArrow()
        }
    }

    init {
        +topElement
        initElements()
        beforeTransform {
            size = back.size
        }
    }

    private fun animateArrow() {
        animate(0.15) {
            arrow.rotation.degrees = if (opened) Math.PI else 0.0
        }
    }

    private fun animateAlpha() {
        elements.children.forEach { element ->
            animate(0.05) {
                element.color.alpha = 0.0
                findText(element)!!.color.alpha = if (opened) 1.0 else 0.0
            }
        }
        animate(0.08) {
            back.color.alpha = if (opened) 0.28 else 0.0
        }
    }

    private fun findText(element: AbstractElement): TextElement? {
        (element as Parent).children.forEach { child ->
            if (child is TextElement) return child
        }
        return null
    }

    private fun initElements() {
        elements.children.clear()
        val width = max(100.0, entries.maxOf { getWidth(it) + 26.0 })
        back.size = V3(width, (entries.size + 1) * 19.0)
        topElement.size.x = width
        entries.forEachIndexed { index, entire ->
            elements + carved {
                carveSize = 2.0
                color = ColorPalette.BLUE.light.apply { alpha = 0.0 }
                size = V3(width, 19.0)
                +text {
                    align = CENTER
                    origin = CENTER
                    content = entire
                    color.alpha = 0.0
                }
                onHover {
                    if (!opened) return@onHover
                    animate(0.08, Easings.QUINT_OUT) {
                        color.alpha = if (hovered) 1.0 else 0.0
                    }
                }
                onMouseUp {
                    if (!opened) return@onMouseUp
                    title.content = findText(this@carved)!!.content
                    activeValueIndex = index

                    opened = false
                    animateAlpha()
                    animateArrow()
                }
            }
        }
        title.content = entries[activeValueIndex]
        if (opened) animateAlpha()
    }
}
