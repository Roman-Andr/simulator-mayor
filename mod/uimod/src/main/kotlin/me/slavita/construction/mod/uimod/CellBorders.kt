package me.slavita.construction.mod.uimod

import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.opengl.GlStateManager.blendFunc
import dev.xdark.clientapi.opengl.GlStateManager.depthMask
import dev.xdark.clientapi.opengl.GlStateManager.disableAlpha
import dev.xdark.clientapi.opengl.GlStateManager.disableCull
import dev.xdark.clientapi.opengl.GlStateManager.disableLighting
import dev.xdark.clientapi.opengl.GlStateManager.disableTexture2D
import dev.xdark.clientapi.opengl.GlStateManager.enableAlpha
import dev.xdark.clientapi.opengl.GlStateManager.enableBlend
import dev.xdark.clientapi.opengl.GlStateManager.enableTexture2D
import dev.xdark.clientapi.opengl.GlStateManager.shadeModel
import dev.xdark.clientapi.render.DefaultVertexFormats
import me.slavita.construction.common.utils.CHANGE_BORDER_CHANNEL
import me.slavita.construction.common.utils.CREATE_BORDER_CHANNEL
import me.slavita.construction.common.utils.DELETE_BORDER_CHANNEL
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.mod.uimod.utils.bufferBuilder
import me.slavita.construction.mod.uimod.utils.color
import me.slavita.construction.mod.uimod.utils.entity
import me.slavita.construction.mod.uimod.utils.prevX
import me.slavita.construction.mod.uimod.utils.prevY
import me.slavita.construction.mod.uimod.utils.prevZ
import me.slavita.construction.mod.uimod.utils.readRgb
import me.slavita.construction.mod.uimod.utils.readUuid
import me.slavita.construction.mod.uimod.utils.readV3
import me.slavita.construction.mod.uimod.utils.tessellator
import me.slavita.construction.mod.uimod.utils.ticks
import org.lwjgl.opengl.GL11
import kotlin.math.abs

object CellBorders : IRegistrable {
    private val borders = hashSetOf<Border>()

    override fun register() {
        mod.registerChannel(CREATE_BORDER_CHANNEL) {
            borders.add(
                Border(
                    readUuid(),
                    readRgb(),
                    readInt(),
                    readDouble(),
                    readDouble(),
                    readV3()
                )
            )
            println("add")
        }

        mod.registerChannel(CHANGE_BORDER_CHANNEL) {
            val uuid = readUuid()
            borders.find { it.uuid == uuid }?.apply {
                color = readRgb()
            }
            println("change")
        }

        mod.registerChannel(DELETE_BORDER_CHANNEL) {
            val uuid = readUuid()
            borders.removeIf { it.uuid == uuid }
            println("removed")
        }
    }

    fun renderBorders() {
        disableLighting()
        disableTexture2D()
        disableAlpha()
        shadeModel(GL11.GL_SMOOTH)
        enableBlend()
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)
        disableCull()
        depthMask(false)

        borders.forEach { border ->
            if (abs(border.location.x - entity.x) <= border.width / 2.0 &&
                abs(border.location.z - entity.z) <= border.width / 2.0
            ) return@forEach

            val x = border.location.x - (entity.x - prevX) * ticks - prevX
            val y = border.location.y - (entity.y - prevY) * ticks - prevY
            val z = border.location.z - (entity.z - prevZ) * ticks - prevZ

            border.run {
                vertices.forEach { side ->
                    bufferBuilder.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR)

                    side.forEachIndexed { index, vertex ->
                        bufferBuilder
                            .pos(x + vertex.x, y + vertex.y, z + vertex.z)
                            .color(color, if (index % 2 == 1) 0 else alpha)
                            .endVertex()
                    }

                    tessellator.draw()
                }
            }
        }

        depthMask(true)
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_CONSTANT_COLOR)
        shadeModel(GL11.GL_FLAT)
        enableTexture2D()
        enableAlpha()
    }
}
