package me.slavita.construction.mod.templates

import me.slavita.construction.mod.utils.ColorPalette
import me.slavita.construction.mod.utils.getWidth
import ru.cristalix.uiengine.element.RectangleElement
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*

inline fun switch(initializer: SwitchElement.() -> Unit) = SwitchElement().also(initializer)

class SwitchElement : RectangleElement() {
	var text = listOf("Пусто")
		set(value) {
			field = value
			redraw()
			relocate()
		}
	var scaleFactor = 1.0
		set(value) {
			field = value
			redraw()
			relocate()
		}
	val activeValue
		get() = text[activeElement]
	private val variants = mutableListOf<RectangleElement>()
	private var action = {}
	private var activeElement = 0
		set(value) {
			field = value
			relocate()
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
		flexSpacing = 0.0
		flexDirection = FlexDirection.RIGHT
	}

	init {
		+back
		back + activeBox
		+container
		redraw()
		relocate()
	}

	private fun relocate() {
		animate(0.25, Easings.CUBIC_OUT) {
			activeBox.size.x = container.children[activeElement].size.x
			activeBox.offset.x = container.children[activeElement].offset.x
		}
	}

	private fun redraw() {
		container.children.clear()
		text.forEachIndexed { index, s ->
			val entry = rectangle {
				size = V3((getWidth(s) + 17.0) * scaleFactor, 19.0 * scaleFactor)
				val title = +text {
					align = CENTER
					origin = CENTER
					content = s
					onMouseUp {
						click(index, this@text)
					}
				}
				onHover {
					title.color.alpha = if (hovered && activeElement != index) 0.62 else 1.0
				}
				onMouseUp {
					click(index, title)
				}
			}
			container + entry
			variants.add(entry)
			back.size = V3(text.sumOf { (getWidth(it) + 17.0) * scaleFactor }, 19.0 * scaleFactor)
		}
		activeBox.size.y = 19.0 * scaleFactor
	}

	fun onSwitch(targetAction: () -> Unit) {
		action = targetAction
	}

	fun click(index: Int, title: TextElement) {
		activeElement = index
		title.color.alpha = 1.0
		action()
	}
}