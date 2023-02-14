package me.slavita.construction.mod

import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.mod.templates.button
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.Context3D
import ru.cristalix.uiengine.utility.CENTER
import ru.cristalix.uiengine.utility.V3

object WorldButton : IRegistrable {
    private val context = Context3D(V3(0.0, 0.0, 0.0)).apply {
        +button {
            align = CENTER
            origin = CENTER
            onButtonClick {
                println("CLICK")
            }
        }
    }

    override fun register() {
        UIEngine.worldContexts.add(context)
    }
}
