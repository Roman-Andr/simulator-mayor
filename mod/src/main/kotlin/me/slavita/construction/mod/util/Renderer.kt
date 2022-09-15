package me.slavita.construction.mod.util

import dev.xdark.clientapi.ClientApi
import dev.xdark.clientapi.opengl.GlStateManager
import dev.xdark.clientapi.render.DefaultVertexFormats
import ru.cristalix.clientapi.JavaMod
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.uiengine.utility.V3

object Renderer {
    fun renderBlockFrame(api: ClientApi, location: V3) {
        val tessellator = api.tessellator()
        val bufferbuilder = tessellator.bufferBuilder
        GlStateManager.disableTexture2D()
        GlStateManager.disableBlend()
        GlStateManager.glLineWidth(3.0f)
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
        val alpha = 64

        for (i in 0..1) {
            bufferbuilder.pos(tx, ty + i, tz).color(0, 255, 0, 0).endVertex()
            bufferbuilder.pos(tx, ty + i, tz + 1.0).color(0, 255, 0, alpha).endVertex()
            bufferbuilder.pos(tx + 1.0, ty + i, tz + 1.0).color(0, 255, 0, alpha).endVertex()
            bufferbuilder.pos(tx + 1.0, ty + i, tz).color(0, 255, 0, alpha).endVertex()
            bufferbuilder.pos(tx, ty + i, tz).color(0, 255, 0, alpha).endVertex()
        }
        for (j1 in 0..1) {
            for (l1 in 0..1) {
                bufferbuilder.pos(tx + j1.toDouble(), ty, tz + l1.toDouble()).color(0, 255, 0, 0).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty, tz + l1.toDouble()).color(0, 255, 0, alpha).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble()).color(0, 255, 0, alpha).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble()).color(0, 255, 0, 0).endVertex()
            }
        }
        tessellator.draw()
        GlStateManager.glLineWidth(3.0f)
        GlStateManager.enableBlend()
        GlStateManager.enableTexture2D()
    }
}