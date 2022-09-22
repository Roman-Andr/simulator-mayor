package me.slavita.construction.mod.util

import dev.xdark.clientapi.ClientApi
import dev.xdark.clientapi.opengl.GlStateManager
import dev.xdark.clientapi.render.DefaultVertexFormats
import ru.cristalix.clientapi.JavaMod.clientApi
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
            -player.lastY - (player.y - player.lastY) * ticks,
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
                bufferbuilder.pos(tx + j1.toDouble(), ty, tz + l1.toDouble()).color(color.red, color.green, color.blue, 0).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty, tz + l1.toDouble()).color(color.red, color.green, color.blue, alpha).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble()).color(color.red, color.green, color.blue, alpha).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble()).color(color.red, color.green, color.blue, 0).endVertex()
            }
        }
        tessellator.draw()
        GlStateManager.glLineWidth(lineWidth)
        GlStateManager.enableBlend()
        GlStateManager.enableTexture2D()
    }
}