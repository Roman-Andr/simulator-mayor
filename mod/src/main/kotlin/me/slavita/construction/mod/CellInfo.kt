package me.slavita.construction.mod

import dev.xdark.feder.NetUtil
import me.slavita.construction.mod.templates.ButtonElement
import me.slavita.construction.mod.templates.button
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.Context3D
import ru.cristalix.uiengine.element.Parent
import ru.cristalix.uiengine.utility.*

inline fun context3d(initializer: Context3D.() -> Unit) = Context3D(V3()).also(initializer)

object CellInfo {
    init {
        mod.registerChannel("test") {
            UIEngine.worldContexts += context3d {
                offset.x = readDouble()
                offset.y = readDouble()
                offset.z = readDouble()
                rotation = Rotation(readDouble(), 0.0, 1.0, 0.0)
                +text {
                    origin = CENTER
                    align = CENTER
                    content = NetUtil.readUtf8(this@registerChannel)
                }
                +button {
                    origin = CENTER
                    align = CENTER
                    targetWidth = 100.0
                }
            }
        }
    }
}