import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.item.Item
import dev.xdark.clientapi.item.ItemStack
import ru.cristalix.clientapi.JavaMod
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ItemElement
import ru.cristalix.uiengine.element.RectangleElement
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
            size = V3(12.0, 12.0, 12.0)
            offset.x -= 2.0
            offset.y -= 25
            color = WHITE
        }
    }

    private var color = Color(100, 100, 0, 65.0)

    private var lineWidth = 3.0f

    init {
        UIEngine.overlayContext.addChild(nextBlock)

        mod.registerChannel("structure:currentBlock") {
            val position = V3(readDouble(), readDouble(), readDouble())
            val typeId = readInt()
            val data = readByte()
            val item = ItemStack.of(Item.of(typeId), 1, data.toInt())
            (nextBlock.children[0] as ItemElement).stack = item
            /*val image: ResourceLocation? = if (readBoolean()) {
                ResourceLocation.of("minecraft", "mcpatcher/cit/others/badges/info1.png")
            } else {
                ResourceLocation.of("minecraft", "mcpatcher/cit/others/badges/close.png")
            }
            (nextBlock.children[1] as RectangleElement).textureLocation = image*/
            currentBlockLocation = position
            nextBlock.enabled = true
        }

        mod.registerChannel("structure:frameColor") {
            color = Color(readInt(), readInt(), readInt(), readDouble())
        }

        mod.registerHandler<RenderPass> {
            if (currentBlockLocation == null) return@registerHandler
            Renderer.renderBlockFrame(JavaMod.clientApi, currentBlockLocation!!, color, lineWidth)
        }

        mod.registerChannel("structure:completed") {
            nextBlock.enabled = false
            currentBlockLocation = null
        }
    }
}