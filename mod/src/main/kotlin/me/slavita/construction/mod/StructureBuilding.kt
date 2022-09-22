package me.slavita.construction.mod

import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.item.Item
import dev.xdark.clientapi.item.ItemStack
import me.slavita.construction.mod.util.Renderer
import ru.cristalix.clientapi.JavaMod
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ItemElement
import ru.cristalix.uiengine.element.RectangleElement
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.utility.Relative
import ru.cristalix.uiengine.utility.*

class StructureBuilding {
    private var currentBlockLocation: V3? = null

    private val nextBlock: RectangleElement = rectangle {
        align = Relative.TOP_LEFT
        origin = Relative.BOTTOM_RIGHT
        offset.x = 80.0
        offset.y = 35.0
        enabled = false
        size.y = 24.0
        addChild(item {
            align = Relative.TOP_RIGHT
            origin = Relative.TOP_RIGHT
            offset.x = 30.0
            scale.x = 2.0
            scale.y = 2.0
        })
        addChild(text {
            content = "???"
            offset.x = -20.0
            scale.x = 2.5
            scale.y = 2.5
            align = Relative.RIGHT
            origin = Relative.RIGHT
            shadow = true
        })
        addChild(text {
            content = "Осталось\nпоставить блоков"
            align = Relative.BOTTOM
            origin = Relative.TOP
            offset.x = -12.0
            offset.y = 12.0
            shadow = true
        })
    }

    private var color: Color = Color(100, 100, 0, 65.0)

    init {
        UIEngine.overlayContext.addChild(nextBlock)

        mod.registerChannel("structure:next") {
            nextBlock.enabled = true
            val x = readInt()
            val y = readInt()
            val z = readInt()

            val typeId = readInt()
            val amount = readInt()
            val data = readByte()

            nextBlock.enabled = true

            val item = Item.of(typeId)
            (nextBlock.children[0] as ItemElement).stack = if (item != null) ItemStack.of(item, 1, data.toInt()) else null
            (nextBlock.children[1] as TextElement).content = "$amount"

            currentBlockLocation = V3(x.toDouble(), y.toDouble(), z.toDouble())
        }

        mod.registerChannel("structure:changeColor") {
            color = Color(
                readInt(),
                readInt(),
                readInt(),
                readDouble(),
            )
        }

        mod.registerChannel("structure:completed") {
            nextBlock.enabled = false
            currentBlockLocation = null
        }

        mod.registerHandler<RenderPass> {
            if (currentBlockLocation == null) return@registerHandler
            Renderer.renderBlockFrame(JavaMod.clientApi, currentBlockLocation!!, color, 3.0f)
        }
    }
}