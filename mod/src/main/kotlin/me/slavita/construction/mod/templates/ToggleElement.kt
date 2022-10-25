package me.slavita.construction.mod.templates

import dev.xdark.clientapi.resource.ResourceLocation
import me.slavita.construction.mod.utils.ColorPalette
import ru.cristalix.clientapi.JavaMod.loadTextureFromJar
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*

inline fun toggle(initializer: ToggleElement.() -> Unit) = ToggleElement().also(initializer)

class ToggleElement : CarvedRectangle() {
	var status = false
	var active = true
		set(value) {
			field = value
			update()
		}
	var action = {}
	private var palette = ColorPalette.BLUE
	private val checkTexture: ResourceLocation = loadTextureFromJar(clientApi, "toggle", "check", "check.png")
	private val crossTexture: ResourceLocation = loadTextureFromJar(clientApi, "toggle", "cross", "cross.png")
	private val check = rectangle {
		align = CENTER
		origin = CENTER
		color = WHITE
		size = V3(7.0, 7.0)
	}
	private val box = carved {
		+check
		carveSize = 2.0
		align = V3(0.25, 0.5)
		origin = CENTER
		size = V3(13.0, 13.0)
		color = ColorPalette.RED.none
	}
	private val back = carved {
		align = CENTER
		origin = CENTER
		carveSize = 2.0
		size = V3(26.0, 13.0)
		+box

		onHover {
			if (!active) return@onHover
			animate(0.15, Easings.QUINT_OUT) {
				update()
			}
		}

		onMouseUp {
			if (!active) return@onMouseUp
			status = !status
			animate(0.15) {
				box.align.x = if (!status) 0.25 else 0.75
				update()
				action()
			}
		}
	}

	init {
		+back
		beforeTransform {
			size = back.size
		}
		update()
	}

	private fun update() {
		check.textureLocation = if (status) checkTexture
		else crossTexture
		if (!active) {
			back.color = ColorPalette.NEUTRAL.none.apply { alpha = 0.28 }
			box.color = ColorPalette.NEUTRAL.none.apply { alpha = 1.0 }
			return
		}
		if (hovered) {
			(if (status) palette.light else ColorPalette.RED.light).run {
				back.color = this.apply { alpha = 0.28 }
				box.color = this.apply { alpha = 0.62 }
			}
		} else {
			(if (status) palette.middle else ColorPalette.RED.none).run {
				back.color = this.apply { alpha = 0.28 }
				box.color = this.apply { alpha = 1.0 }
			}
		}
	}

	fun onChange(value: () -> Unit) {
		action = value
	}
}