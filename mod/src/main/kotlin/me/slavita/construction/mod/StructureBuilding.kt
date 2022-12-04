package me.slavita.construction.mod

import dev.xdark.clientapi.event.block.BlockRightClick
import dev.xdark.clientapi.event.lifecycle.GameTickPost
import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.item.ItemStack
import dev.xdark.clientapi.item.ItemTools
import dev.xdark.clientapi.opengl.GlStateManager
import dev.xdark.clientapi.util.EnumHand
import io.netty.buffer.Unpooled
import me.slavita.construction.mod.utils.Renderer
import me.slavita.construction.mod.utils.extensions.InventoryExtensions.blocksCount
import me.slavita.construction.mod.utils.extensions.InventoryExtensions.handItemEquals
import me.slavita.construction.mod.utils.extensions.InventoryExtensions.hotbarEqualSlots
import me.slavita.construction.mod.utils.extensions.PositionExtensions.equalsV
import org.lwjgl.input.Mouse
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.ItemElement
import ru.cristalix.uiengine.element.RectangleElement
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.eventloop.thenAnimate
import ru.cristalix.uiengine.eventloop.thenWait
import ru.cristalix.uiengine.utility.*

object StructureBuilding {

    private var currentItem: ItemStack? = null
    private var currentBlockLocation: V3? = null
    private var hoverText: String? = null
    private var targetText: String? = null
    private var cooldownExpired = true
    private var frameColor = Color(0, 0, 0, 65.0)
    private var lastMarkersSlots = arrayOf<Int>()
    private var lineWidth = 3.5F

    private val nextBlock: RectangleElement = rectangle {

        align = BOTTOM
        offset = V3(-105.0, -2.0)
        enabled = false

        +item {
            align = BOTTOM
            origin = BOTTOM_RIGHT
            scale = V3(2.5, 2.5, 1.0)
            onHover {
                hoverText = if (hovered) targetText else null
            }
        }
        +rectangle {
            align = TOP_RIGHT
            origin = BOTTOM_LEFT
            size = V3(10.0, 10.0, 1.0)
            offset.x -= 5.0
            offset.y -= 25
            color = WHITE
            beforeRender { GlStateManager.disableDepth() }
            afterRender { GlStateManager.enableDepth() }
        }
        +text {
            align = BOTTOM_RIGHT
            origin = TOP_LEFT
            size = V3(12.0, 12.0, 1.0)
            offset.x -= 18.0
            offset.y -= 9
            color = WHITE
            shadow = true
            beforeRender { GlStateManager.disableDepth() }
            afterRender { GlStateManager.enableDepth() }
        }
    }

    private val markers: RectangleElement = rectangle {
        align = BOTTOM
        offset = V3(-80.0, -24.0)
    }

    init {
        UIEngine.overlayContext.addChild(nextBlock)
        UIEngine.overlayContext.addChild(markers)

        mod.registerChannel("structure:currentBlock") {
            val position = V3(readDouble(), readDouble(), readDouble())
            cooldownExpired = currentBlockLocation == null
            currentItem = ItemTools.read(this).apply { targetText = this.displayName }

            (nextBlock.children[0] as ItemElement).stack = currentItem
            updateInfoIcon()

            currentBlockLocation = position
            lastMarkersSlots = arrayOf()
            markers.children.clear()
            nextBlock.enabled = true
        }

        mod.registerChannel("structure:cooldown") {
            cooldownExpired = true
        }

        mod.registerChannel("structure:hide") {
            nextBlock.enabled = false
            currentBlockLocation = null
            markers.children.clear()
        }

        mod.runRepeatingTask(.0, .9) {
            markers.children.forEach { marker ->
                marker.animate(0.4, Easings.CUBIC_OUT) {
                    offset.y += 4
                }.thenWait(0.05).thenAnimate(0.4, Easings.CUBIC_OUT) {
                    offset.y -= 4
                }
            }
        }

        mod.registerHandler<BlockRightClick> {
            if (currentBlockLocation == null || hand == EnumHand.OFF_HAND) return@registerHandler
            if (!position.add(facing.xOffset, facing.yOffset, facing.zOffset)
                    .equalsV(currentBlockLocation!!)
            ) return@registerHandler
            if (!player.inventory.handItemEquals(currentItem!!)) return@registerHandler

            clientApi.clientConnection().sendPayload("structure:place", Unpooled.buffer())
            player.swingArm(EnumHand.MAIN_HAND)
        }

        mod.registerHandler<GameTickPost> {
            if (currentBlockLocation == null) return@registerHandler

            updateInfoIcon()

            val targetColor = if (!player.inventory.handItemEquals(currentItem!!)) SpecialColor.RED
            else if (!cooldownExpired) SpecialColor.GOLD
            else SpecialColor.GREEN
            frameColor = targetColor.toColor()

            player.inventory.hotbarEqualSlots(currentItem!!).toTypedArray().apply {
                if (this contentEquals lastMarkersSlots) return@registerHandler
                lastMarkersSlots = this

                markers.children.clear()
                this.forEach { slot ->
                    markers.addChild(createMarker(slot))
                }
            }
        }

        mod.registerHandler<RenderPass> {
            if (currentBlockLocation == null) return@registerHandler
            Renderer.renderBlockFrame(clientApi, currentBlockLocation!!, frameColor, lineWidth)
        }

        UIEngine.postOverlayContext.afterRender {
            clientApi.resolution().run {
                if (hoverText == null) return@afterRender
                clientApi.minecraft().currentScreen()?.drawHoveringText(
                    hoverText, Mouse.getX() / scaleFactor,
                    (clientApi.resolution().scaledHeight_double - Mouse.getY() / scaleFactor).toInt()
                )
            }
        }
    }

    private fun updateInfoIcon() {
        player.inventory.blocksCount(currentItem!!).run {
            (nextBlock.children[1] as RectangleElement).textureLocation =
                if (this > 0) Resources.INFO.source
                else Resources.CANCEL.source
            (nextBlock.children[2] as TextElement).content = if (this > 0) this.toString() else ""
        }
    }

    private fun createMarker(slotId: Int): RectangleElement {
        return rectangle {
            origin = CENTER
            textureLocation = Resources.ARROW.source
            size = V3(16.0, 16.0, 1.0)
            offset.x = slotId * 20.0
            color = WHITE

            beforeRender { GlStateManager.disableDepth() }
            afterRender { GlStateManager.enableDepth() }
        }
    }
}