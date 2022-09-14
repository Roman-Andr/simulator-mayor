package me.slavita.construction.mod

import dev.xdark.clientapi.event.network.ServerConnect
import dev.xdark.clientapi.event.render.RenderPass
import me.slavita.construction.mod.util.Renderer
import ru.cristalix.clientapi.JavaMod
import ru.cristalix.clientapi.registerHandler
import ru.cristalix.uiengine.utility.V3

class StructureBuilding {
    var currentBlockLocation: V3? = null

    init {
        /*registerHandler<RenderPass> {
            if (currentBlockLocation == null) return@registerHandler

            //val x = currentBlockLocation!!.x.toInt() +
            //Renderer.renderBlockFrame(JavaMod.clientApi, x, y, z)
        }

        mod.registerChannel("structure:next") {
            val x = getInt(0)
            val y = getInt(1)
            val z = getInt(2)
            val blockId = getByte(3)
            val blockData = getByte(4)

            currentBlockLocation = V3(x.toDouble(), y.toDouble(), z.toDouble())
        }*/
    }
}