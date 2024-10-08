package me.slavita.construction.mod.uimod.templates

import dev.xdark.clientapi.event.lifecycle.GameLoop
import me.slavita.construction.mod.uimod.mod
import me.slavita.construction.mod.uimod.utils.inBox
import ru.cristalix.uiengine.UIEngine.clientApi

inline fun infoZone(initializer: InfoZone.() -> Unit) = InfoZone().also(initializer)

class InfoZone {
    val info = info {}

    init {
        mod.registerHandler<GameLoop> {
            info.run {
                clientApi.minecraft().mouseOver.pos?.run {
                    if (boxes.any { inBox(it.min, it.max) } && !turn) {
                        title(boxes.find { inBox(it.min, it.max) }!!.title)
                        turn = true
                        show()
                    }
                    if (!boxes.any { inBox(it.min, it.max) } && turn) {
                        turn = false
                        hide()
                    }
                }
            }
        }
    }
}
