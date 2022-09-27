package me.slavita.construction.mod

import dev.xdark.clientapi.event.render.RenderPass
import me.slavita.construction.mod.utils.extensions.PlayerExtensions.blocksCount
import dev.xdark.clientapi.item.ItemTools
import dev.xdark.clientapi.opengl.GlStateManager
import dev.xdark.clientapi.resource.ResourceLocation
import me.slavita.construction.mod.utils.Renderer
import ru.cristalix.clientapi.JavaMod
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ItemElement
import ru.cristalix.uiengine.element.RectangleElement
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.utility.*

class StructureBuilding {

    private var currentBlockLocation: V3? = null

    private val nextBlock: RectangleElement = rectangle {

        align = Relative.BOTTOM
        offset = V3(-105.0, -2.0)
        enabled = false

        +item {
            align = Relative.BOTTOM
            origin = Relative.BOTTOM_RIGHT
            scale = V3(2.5, 2.5, 1.0)
        }
        +rectangle {
            align = Relative.TOP_RIGHT
            origin = Relative.BOTTOM_LEFT
            size = V3(12.0, 12.0, 1.0)
            offset.x -= 2.0
            offset.y -= 25
            color = WHITE
        }
        +text {
            align = Relative.BOTTOM_RIGHT
            origin = Relative.TOP_LEFT
            size = V3(12.0, 12.0, 1.0)
            offset.x -= 18.0
            offset.y -= 9
            color = WHITE
            beforeRender { GlStateManager.disableDepth() }
            afterRender { GlStateManager.enableDepth() }
        }
    }

    private var color = Color(0, 0, 0, 65.0)

    private var lineWidth = 3.4f

    init {
        UIEngine.overlayContext.addChild(nextBlock)

        mod.registerChannel("structure:currentBlock") {
            val position = V3(readDouble(), readDouble(), readDouble())
            val item = ItemTools.read(this)
            val blocksCount = clientApi.minecraft().player.blocksCount(item, readBoolean())

            val image = ResourceLocation.of("minecraft",
                if (blocksCount > 0) "mcpatcher/cit/others/badges/info1.png"
                else "mcpatcher/cit/others/badges/close.png"
            )

            (nextBlock.children[0] as ItemElement).stack = item
            (nextBlock.children[1] as RectangleElement).textureLocation = image
            (nextBlock.children[2] as TextElement).content = if (blocksCount > 0) blocksCount.toString() else ""

            currentBlockLocation = position
            nextBlock.enabled = true
        }

        mod.registerChannel("structure:frameColor") {
            color = Color(readInt(), readInt(), readInt(), readDouble())
        }

        mod.registerChannel("structure:completed") {
            nextBlock.enabled = false
            currentBlockLocation = null
        }

        mod.registerHandler<RenderPass> {
            if (currentBlockLocation == null) return@registerHandler
            Renderer.renderBlockFrame(clientApi, currentBlockLocation!!, color, lineWidth)
        }
    }
}