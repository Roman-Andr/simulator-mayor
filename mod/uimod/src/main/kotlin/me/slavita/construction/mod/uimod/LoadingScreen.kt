package me.slavita.construction.mod.uimod

import me.slavita.construction.common.utils.LOADING_STATE_CHANNEL
import me.slavita.construction.common.utils.LoadingState
import me.slavita.construction.mod.uimod.utils.runRepeatingTask
import me.slavita.construction.mod.uimod.utils.runTask
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ContextGui
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.utility.CENTER
import ru.cristalix.uiengine.utility.Color
import ru.cristalix.uiengine.utility.Easings
import ru.cristalix.uiengine.utility.TOP_LEFT
import ru.cristalix.uiengine.utility.V3
import ru.cristalix.uiengine.utility.rectangle
import ru.cristalix.uiengine.utility.text

class LoadingScreen : ContextGui() {
    private val parent = rectangle {
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

    private var text = ""
        set(value) {
            (parent.children[0] as TextElement).content = value
            field = value
        }

    private var subText = ""
        set(value) {
            (parent.children[1] as TextElement).content = value
            field = value
        }

    init {
        UIEngine.overlayContext.addChild(parent)
        open()

        var loadingAnimation = true
        val loadingAnimationTask = runRepeatingTask(0.7, 0.7) {
            if (!loadingAnimation) return@runRepeatingTask
            text = if (text.contains("...")) "." else "$text."
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
                    runTask(1.4) {
                        close()
                    }
                }
            }
        }
    }

    fun show() {
        parent.animate(1, Easings.CUBIC_OUT) {
            color.alpha = 1.0
            parent.children.forEach { it.color.alpha = 1.0 }
        }
    }

    private fun hide() {
        parent.animate(1.5, Easings.CUBIC_OUT) {
            color.alpha = 0.0
            parent.children.forEach { it.color.alpha = 0.0 }
        }
    }
}
