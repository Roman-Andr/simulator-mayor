package me.slavita.construction.mod

import me.slavita.construction.common.utils.LOADING_STATE_CHANNEL
import me.slavita.construction.common.utils.LoadingState
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.utility.*
import java.util.*

object LoadingScreen {
    val screen = rectangle {
        align = TOP_LEFT
        offset = V3(0.0, 0.0)
        size = UIEngine.overlayContext.size
        color = Color(48, 44, 52, 0.0)
        enabled = true

        +text {
            align = CENTER
            origin = CENTER
            offset = V3(0.0, -10.0)
            scale = V3(2.5, 2.5)
            color = Color(255, 255, 255, 0.0)
            content = "Загружаем данные..."
        }

        +text {
            align = CENTER
            origin = CENTER
            color = Color(255, 255, 255, 0.0)
            offset = V3(0.0, 10.0)
            content = ""
        }
    }

    var text = ""
        set(value) {
            (screen.children[0] as TextElement).content = value
            field = value
        }

    var subText = ""
        set(value) {
            (screen.children[1] as TextElement).content = value
            field = value
        }

    init {
        UIEngine.overlayContext.addChild(screen)

        var loadingAnimation = true
        val loadingAnimationTask = mod.runRepeatingTask(1.0, 1.0) {
            if (!loadingAnimation) return@runRepeatingTask
            if (text.contains("...")) {
                text.replace("...", "")
            } else {
                text += "."
            }
        }

        mod.registerChannel(LOADING_STATE_CHANNEL) {
            when (LoadingState.values()[readInt()]) {
                LoadingState.TRY_GET -> {
                    text = "Загружаем данные"
                    loadingAnimation = true
                }
                LoadingState.RETRY -> {
                    text = "Что то пошло не так..."
                    subText = "Мы усердно пытаемся загрузить эти данные, пожалуйста, подождите"
                    loadingAnimation = false
                }
                LoadingState.STRUCTURES -> {
                    text = "Почти готово!"
                    subText = "Осталась совсем чуть-чуть..."
                    loadingAnimation = false
                }
                LoadingState.FINISHED -> {
                    loadingAnimationTask.cancel()
                    hide()
                }
            }
        }
    }

    fun show() {
        screen.animate(1, Easings.CUBIC_OUT) {
            color.alpha += 1
            screen.children.forEach { it.color.alpha += 1 }
        }
    }

    fun hide() {
        screen.animate(1.5, Easings.CUBIC_OUT) {
            color.alpha -= 1
            screen.children.forEach { it.color.alpha -= 1 }
        }
    }
}
