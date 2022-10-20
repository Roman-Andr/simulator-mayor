package me.slavita.construction.mod.showcase

import com.google.gson.Gson
import dev.xdark.clientapi.event.lifecycle.GameLoop
import dev.xdark.feder.NetUtil
import me.slavita.construction.mod.mod
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.utility.*

object Showcases {
    private lateinit var showcaseText: TextElement
    private var showcaseBox = carved {
        size = V3(210.0, 40.0)
        align = BOTTOM
        origin = BOTTOM
        offset.y -= 65.0
        color = Color(0, 0, 0, 0.52)
        enabled = false
        showcaseText = +text {
            origin = TOP
            align = TOP
            offset.y += 4.0
            color = Color(0, 255, 0, 1.0)
        }
        +text {
            origin = BOTTOM
            align = BOTTOM
            offset.y -= 4.0
            color = Color(255, 255, 255, 1.0)
            content = "Открыть витрину [ПКМ]"
        }
    }

    init {
        var showcases: Array<ShowcaseData>? = null

        UIEngine.overlayContext.addChild(showcaseBox)

        mod.registerHandler<GameLoop> {
            clientApi.minecraft().mouseOver.pos?.run {
                var shown = false

                showcases?.forEach {
                    val a = it.min
                    val b = it.max
                    if (a.x <= x && x <= b.x && a.y <= y && y <= b.y && a.z <= z && z <= b.z) {
                        showcaseText.content = it.title
                        showcaseBox.enabled = true
                        shown = true
                    }
                }

                if (!shown && showcaseBox.enabled) {
                    showcaseBox.enabled = false
                }
            }
        }

        mod.registerChannel("showcase") {
            showcases = Gson().fromJson(NetUtil.readUtf8(this), Array<ShowcaseData>::class.java)
        }
    }
}