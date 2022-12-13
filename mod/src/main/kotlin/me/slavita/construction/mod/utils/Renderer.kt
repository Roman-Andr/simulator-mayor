package me.slavita.construction.mod.utils

import dev.xdark.clientapi.ClientApi
import dev.xdark.clientapi.math.Vec3d
import dev.xdark.clientapi.opengl.GlStateManager
import dev.xdark.clientapi.render.BufferBuilder
import dev.xdark.clientapi.render.DefaultVertexFormats
import dev.xdark.clientapi.render.Tessellator
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.utility.Color
import ru.cristalix.uiengine.utility.V3


object Renderer {

    fun renderBlockFrame(api: ClientApi, location: V3, color: Color, lineWidth: Float) {

        val tessellator = api.tessellator()
        val bufferbuilder = tessellator.bufferBuilder
        GlStateManager.disableTexture2D()
        GlStateManager.disableBlend()
        GlStateManager.glLineWidth(lineWidth)
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR)

        val player = clientApi.minecraft().renderViewEntity
        val ticks = clientApi.minecraft().timer.renderPartialTicks

        GlStateManager.translate(
            -player.lastX - (player.x - player.lastX) * ticks,
            -player.lastY - (player.y - player.lastY) * ticks + 0.0005,
            -player.lastZ - (player.z - player.lastZ) * ticks,
        )

        val tx = location.x
        val ty = location.y
        val tz = location.z
        val alpha = color.alpha.toInt()

        for (i in 0..1) {
            bufferbuilder.pos(tx, ty + i, tz).color(color.red, color.green, color.blue, 0).endVertex()
            bufferbuilder.pos(tx, ty + i, tz + 1.0).color(color.red, color.green, color.blue, alpha).endVertex()
            bufferbuilder.pos(tx + 1.0, ty + i, tz + 1.0).color(color.red, color.green, color.blue, alpha).endVertex()
            bufferbuilder.pos(tx + 1.0, ty + i, tz).color(color.red, color.green, color.blue, alpha).endVertex()
            bufferbuilder.pos(tx, ty + i, tz).color(color.red, color.green, color.blue, alpha).endVertex()
        }

        for (j1 in 0..1) {
            for (l1 in 0..1) {
                bufferbuilder.pos(tx + j1.toDouble(), ty, tz + l1.toDouble())
                    .color(color.red, color.green, color.blue, 0).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty, tz + l1.toDouble())
                    .color(color.red, color.green, color.blue, alpha).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble())
                    .color(color.red, color.green, color.blue, alpha).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble())
                    .color(color.red, color.green, color.blue, 0).endVertex()
            }
        }

        tessellator.draw()
        GlStateManager.glLineWidth(lineWidth)
        GlStateManager.enableBlend()
        GlStateManager.enableTexture2D()
    }

    fun drawLine(pos1: V3, pos2: V3, color: Color, width: Float) {

        val tessellator = clientApi.tessellator()
        val bufferbuilder = tessellator.bufferBuilder
        GlStateManager.disableTexture2D()
        GlStateManager.disableBlend()
        GlStateManager.glLineWidth(width)
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR)

        val player = clientApi.minecraft().renderViewEntity
        val ticks = clientApi.minecraft().timer.renderPartialTicks

        GlStateManager.translate(
            -player.lastX - (player.x - player.lastX) * ticks,
            -player.lastY - (player.y - player.lastY) * ticks + 0.0005,
            -player.lastZ - (player.z - player.lastZ) * ticks,
        )

        bufferbuilder.pos(pos1.x, pos1.y, pos1.z).color(color.red, color.green, color.blue, 0).endVertex()
        bufferbuilder.pos(pos2.x, pos2.y, pos2.z).color(color.red, color.green, color.blue, color.alpha.toInt()).endVertex()

        tessellator.draw()
        GlStateManager.glLineWidth(width)
        GlStateManager.enableBlend()
        GlStateManager.enableTexture2D()
    }
}
