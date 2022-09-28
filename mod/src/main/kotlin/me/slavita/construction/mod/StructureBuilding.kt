package me.slavita.construction.mod

import dev.xdark.clientapi.entity.DataParameter
import dev.xdark.clientapi.event.entity.EntityDataChange
import dev.xdark.clientapi.event.lifecycle.GameTickPost
import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.item.ItemStack
import me.slavita.construction.mod.utils.extensions.PlayerExtensions.blocksCount
import dev.xdark.clientapi.item.ItemTools
import dev.xdark.clientapi.opengl.GlStateManager
import dev.xdark.clientapi.resource.ResourceLocation
import me.slavita.construction.mod.utils.Renderer
import me.slavita.construction.mod.utils.extensions.PlayerExtensions.hotbarEqualSlots
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ItemElement
import ru.cristalix.uiengine.element.RectangleElement
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.eventloop.thenAnimate
import ru.cristalix.uiengine.eventloop.thenWait
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

    private val markers: RectangleElement = rectangle {
        align = Relative.BOTTOM
        offset = V3(-88.0, -32.0)
    }

    private var frameColor = Color(0, 0, 0, 65.0)
    private var currentItem: ItemStack? = null
    private var currentItemColorable: Boolean? = null
    private var lastMarkersSlots = arrayOf<Int>()
    private var lineWidth = 3.4f

    init {
        UIEngine.overlayContext.addChild(nextBlock)
        UIEngine.overlayContext.addChild(markers)

        mod.registerChannel("structure:currentBlock") {
            val position = V3(readDouble(), readDouble(), readDouble())
            currentItem = ItemTools.read(this)!!
            currentItemColorable = readBoolean()

            (nextBlock.children[0] as ItemElement).stack = currentItem

            currentBlockLocation = position
            nextBlock.enabled = true
        }

        mod.runRepeatingTask(0.0, 1.2) {
            markers.children.forEach { marker ->
                marker.animate(0.5, Easings.CUBIC_OUT) {
                    offset.y += 4
                }.thenWait(0.1).thenAnimate(0.5, Easings.CUBIC_OUT) {
                    offset.y -= 4
                }
            }
        }

        mod.registerHandler<GameTickPost> {
            if (currentItem == null) return@registerHandler

            val blocksCount = player.inventory.blocksCount(currentItem!!, currentItemColorable!!)
            val image = ResourceLocation.of("minecraft",
                if (blocksCount > 0) "mcpatcher/cit/others/badges/info1.png"
                else "mcpatcher/cit/others/badges/close.png"
            )
            (nextBlock.children[1] as RectangleElement).textureLocation = image
            (nextBlock.children[2] as TextElement).content = if (blocksCount > 0) blocksCount.toString() else ""

            val slots = player.inventory.hotbarEqualSlots(currentItem!!, currentItemColorable!!).toTypedArray()
            if (slots contentEquals lastMarkersSlots) return@registerHandler
            lastMarkersSlots = slots

            markers.removeChild(*markers.children.toTypedArray())
            slots.forEach {
                markers.addChild(rectangle {
                    textureLocation = ResourceLocation.of("minecraft", "mcpatcher/cit/others/badges/arrow_down.png")
                    size = V3(16.0, 16.0, 1.0)
                    offset.x = it * 20.0
                    color = WHITE

                    beforeRender { GlStateManager.disableDepth() }
                    afterRender { GlStateManager.enableDepth() }
                })
            }
        }

        mod.registerChannel("structure:frameColor") {
            frameColor = Color(readInt(), readInt(), readInt(), readDouble())
        }

        mod.registerChannel("structure:completed") {
            nextBlock.enabled = false
            currentBlockLocation = null
            markers.removeChild(*markers.children.toTypedArray())
        }

        mod.registerHandler<RenderPass> {
            if (currentBlockLocation == null) return@registerHandler
            Renderer.renderBlockFrame(clientApi, currentBlockLocation!!, frameColor, lineWidth)
        }
    }
}