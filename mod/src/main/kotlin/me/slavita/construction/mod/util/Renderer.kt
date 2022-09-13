package me.slavita.construction.mod.util

import dev.xdark.clientapi.ClientApi
import dev.xdark.clientapi.opengl.GlStateManager
import dev.xdark.clientapi.render.DefaultVertexFormats

object Renderer {
    fun renderBlock(api: ClientApi, blockX: Int, blockY: Int, blockZ: Int) {
        val tessellator = api.tessellator()
        val bufferbuilder = tessellator.bufferBuilder
        GlStateManager.disableTexture2D()
        GlStateManager.disableBlend()
        GlStateManager.glLineWidth(1.0f)
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR)
        val tx = blockX.toDouble()
        val ty = blockY.toDouble()
        val tz = blockZ.toDouble()
        val alpha = 64

        for (i in 0..1) {
            bufferbuilder.pos(tx, ty + i, tz).color(255, 0, 0, 0).endVertex()
            bufferbuilder.pos(tx, ty + i, tz + 1.0).color(255, 0, 0, alpha).endVertex()
            bufferbuilder.pos(tx + 1.0, ty + i, tz + 1.0).color(255, 0, 0, alpha).endVertex()
            bufferbuilder.pos(tx + 1.0, ty + i, tz).color(255, 0, 0, alpha).endVertex()
            bufferbuilder.pos(tx, ty + i, tz).color(255, 0, 0, alpha).endVertex()
        }
        for (j1 in 0..1) {
            for (l1 in 0..1) {
                bufferbuilder.pos(tx + j1.toDouble(), ty, tz + l1.toDouble()).color(255, 0, 0, 0).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty, tz + l1.toDouble()).color(255, 0, 0, alpha).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble()).color(255, 0, 0, alpha).endVertex()
                bufferbuilder.pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble()).color(255, 0, 0, 0).endVertex()
            }
        }
        tessellator.draw()
        GlStateManager.glLineWidth(1.0f)
        GlStateManager.enableBlend()
        GlStateManager.enableTexture2D()
    }
}