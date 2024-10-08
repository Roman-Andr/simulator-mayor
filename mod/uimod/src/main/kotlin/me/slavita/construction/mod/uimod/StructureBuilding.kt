package me.slavita.construction.mod.uimod

import dev.xdark.clientapi.event.input.RightClick
import dev.xdark.clientapi.event.lifecycle.GameTickPost
import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.item.ItemStack
import dev.xdark.clientapi.item.ItemTools
import dev.xdark.clientapi.opengl.GlStateManager
import dev.xdark.clientapi.util.EnumHand
import dev.xdark.clientapi.util.ParticleType
import io.netty.buffer.Unpooled
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.common.utils.STRUCTURE_BLOCK_CHANNEL
import me.slavita.construction.common.utils.STRUCTURE_HIDE_CHANNEL
import me.slavita.construction.common.utils.STRUCTURE_PLACE_CHANNEL
import me.slavita.construction.mod.uimod.utils.Renderer
import me.slavita.construction.mod.uimod.utils.blocksCount
import me.slavita.construction.mod.uimod.utils.handItemEquals
import me.slavita.construction.mod.uimod.utils.hotbarEqualSlots
import me.slavita.construction.mod.uimod.utils.isLookingAt
import me.slavita.construction.mod.uimod.utils.runRepeatingTask
import me.slavita.construction.mod.uimod.utils.sendPayload
import me.slavita.construction.mod.uimod.utils.toColor
import org.lwjgl.input.Mouse
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.ItemElement
import ru.cristalix.uiengine.element.RectangleElement
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.eventloop.thenAnimate
import ru.cristalix.uiengine.eventloop.thenWait
import ru.cristalix.uiengine.utility.BOTTOM
import ru.cristalix.uiengine.utility.BOTTOM_LEFT
import ru.cristalix.uiengine.utility.BOTTOM_RIGHT
import ru.cristalix.uiengine.utility.CENTER
import ru.cristalix.uiengine.utility.Color
import ru.cristalix.uiengine.utility.Easings
import ru.cristalix.uiengine.utility.TOP_LEFT
import ru.cristalix.uiengine.utility.TOP_RIGHT
import ru.cristalix.uiengine.utility.V3
import ru.cristalix.uiengine.utility.WHITE
import ru.cristalix.uiengine.utility.item
import ru.cristalix.uiengine.utility.rectangle
import ru.cristalix.uiengine.utility.text

object StructureBuilding : IRegistrable {

    private var currentBlockLocation: V3? = null
    private var frameColor = Color(0, 0, 0, 65.0)
    private var lineWidth = 4.0F
    private var currentItem: ItemStack? = null
    private var hoverText: String? = null
    private var targetText: String? = null
    private var lastMarkersSlots = arrayOf<Int>()
    private var placed = true

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

    override fun register() {
        UIEngine.overlayContext.addChild(nextBlock)
        UIEngine.overlayContext.addChild(markers)

        mod.registerChannel(STRUCTURE_BLOCK_CHANNEL) {
            val position = V3(readDouble(), readDouble(), readDouble())
            currentItem = ItemTools.read(this).apply { targetText = this.displayName }

            (nextBlock.children[0] as ItemElement).stack = currentItem
            updateInfoIcon()

            currentBlockLocation = position
            lastMarkersSlots = arrayOf()
            markers.children.clear()
            nextBlock.enabled = true
            placed = false
        }

        mod.registerChannel(STRUCTURE_HIDE_CHANNEL) {
            nextBlock.enabled = false
            currentBlockLocation = null
            markers.children.clear()
        }

        runRepeatingTask(.0, .9) {
            markers.children.forEach { marker ->
                marker.animate(0.4, Easings.CUBIC_OUT) {
                    offset.y += 4
                }.thenWait(0.05).thenAnimate(0.4, Easings.CUBIC_OUT) {
                    offset.y -= 4
                }
            }
        }

        mod.registerHandler<RightClick> {
            if (currentBlockLocation == null || hand == EnumHand.OFF_HAND) return@registerHandler
            if (!clientApi.isLookingAt(currentBlockLocation!!)) return@registerHandler
            if (!player.inventory.handItemEquals(currentItem!!)) {
                currentBlockLocation?.run {
                    repeat(10) {
                        clientApi.minecraft().particleManager.spawnEffectParticle(
                            ParticleType.SMOKE_NORMAL, x + 0.5, y + 1.5, z + 0.5, 0.0, 0.0, 0.0, 1
                        )
                    }
                }
                return@registerHandler
            }
            if (placed) return@registerHandler
            sendPayload(STRUCTURE_PLACE_CHANNEL, Unpooled.buffer())
            placed = true
            player.swingArm(EnumHand.MAIN_HAND)
        }

        mod.registerHandler<GameTickPost> {
            if (currentBlockLocation == null) return@registerHandler

            updateInfoIcon()

            val targetColor = if (!player.inventory.handItemEquals(currentItem!!)) GlowColor.RED_LIGHT
            else GlowColor.GREEN_LIGHT
            frameColor = targetColor.toColor().apply { alpha = 65.0 }

            player.inventory.hotbarEqualSlots(currentItem!!).toTypedArray().run {
                if (this contentEquals lastMarkersSlots) return@registerHandler
                lastMarkersSlots = this

                markers.children.clear()
                this.forEach { slot ->
                    markers.addChild(createMarker(slot))
                }
            }
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

    fun RenderPass.renderBlockTip() {
        if (currentBlockLocation == null) return
        Renderer.renderBlockFrame(
            currentBlockLocation!!,
            frameColor,
            lineWidth
        )
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
