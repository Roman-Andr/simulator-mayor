package me.slavita.construction.mod

import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.item.Item
import dev.xdark.clientapi.item.ItemStack
import dev.xdark.clientapi.math.Vec3i
import me.slavita.construction.mod.util.Renderer
import ru.cristalix.clientapi.JavaMod
import ru.cristalix.clientapi.readVarInt
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ItemElement
import ru.cristalix.uiengine.element.RectangleElement
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.utility.*
import java.util.Vector

object BlocksLeft {
    private val blocksToHide: RectangleElement = rectangle {
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

    private val blocks = mutableListOf<Vec3i>()

    init {
        UIEngine.overlayContext.addChild(blocksToHide)

        mod.registerChannel("structure:next") {
            blocksToHide.enabled = true
            val x = readInt()
            val y = readInt()
            val z = readInt()
            blocks.add(Vec3i.of(x, y, z))
            val typeId = readInt()
            val amount = readInt()
            val data = readByte()
            blocksToHide.enabled = true
            (blocksToHide.children[0] as ItemElement).stack = ItemStack.of(Item.of(typeId), 1, data.toInt())
            (blocksToHide.children[1] as TextElement).content = "$amount"
        }

        mod.registerChannel("blocks-left:hide") {
            blocksToHide.enabled = false
        }

//        mod.registerHandler<RenderPass> {
//            blocks.forEach {
//                Renderer.renderBlockFrame(JavaMod.clientApi, it.x, it.y, it.z)
//            }
//        }
    }
}