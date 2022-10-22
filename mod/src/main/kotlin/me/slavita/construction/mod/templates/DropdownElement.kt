package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import me.slavita.construction.mod.utils.doubleVec
import me.slavita.construction.mod.utils.getWidth
import org.lwjgl.input.Mouse
import ru.cristalix.clientapi.JavaMod
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.UIEngine.animationContext
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.AbstractElement
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.Parent
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.eventloop.thenAnimate
import ru.cristalix.uiengine.eventloop.thenWait
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*
import kotlin.math.max

inline fun dropdown(initializer: DropdownElement.() -> Unit) = DropdownElement().also(initializer)

class DropdownElement : CarvedRectangle() {
    var disable = false
        set(value) {
            color =  if (value) ColorPalette.NEUTRAL.none.apply { alpha = 1.0 } else color
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
        offset.x = 12.0
        content = placeholder
    }
    private val arrow = rectangle {
        textureLocation = JavaMod.loadTextureFromJar(clientApi, "dropdown", "arrow", "arrow.png")
        align = RIGHT
        origin = CENTER
        offset.x = -20.0
        color = WHITE
        size = 16.0.doubleVec()
    }
    private val back = carved {
        carveSize = 4.0
        align = TOP
        origin = TOP
        color = ColorPalette.BLUE.none.apply { alpha = 0.0 }
        size = V3(179.0, 38.0)
    }
    private val elements = flex {
        flexDirection = FlexDirection.DOWN
        flexSpacing = 0.0
        align = BOTTOM
        origin = TOP
    }


    init {
        +back
        carveSize = 4.0
        color = ColorPalette.BLUE.middle
        size = V3(179.0, 38.0)
        +title
        +arrow
        +elements
        initElements()

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

    private fun animateArrow() {
        animate(0.15) {
            arrow.rotation.degrees = if (opened) Math.PI else 0.0
        }
    }

    private fun animateAlpha() {
        elements.children.forEach {
            animate(0.05) {
                it.color.alpha = 0.0
                findText(it)!!.color.alpha = if (opened) 1.0 else 0.0
            }
        }
        animate(0.08) {
            back.color.alpha = if (opened) 0.28 else 0.0
        }
    }

    private fun findText(element: AbstractElement): TextElement? {
        (element as Parent).children.forEach {
            if (it is TextElement) return it
        }
        return null
    }

    private fun initElements() {
        elements.children.clear()
        val width = max(179.0, entries.maxOf { getWidth(it) + 52.0 })
        back.size = V3(width, (entries.size + 1) * 38.0)
        size.x = width
        entries.forEachIndexed { index, entrie ->
            elements + carved {
                +text {
                    align = CENTER
                    origin = CENTER
                    content = entrie
                    color.alpha = 0.0
                }
                carveSize = 4.0
                color = ColorPalette.BLUE.light.apply { alpha = 0.0 }
                size = V3(width, 38.0)
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