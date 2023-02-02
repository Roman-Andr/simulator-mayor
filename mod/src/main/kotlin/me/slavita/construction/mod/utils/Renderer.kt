package me.slavita.construction.mod.utils

import dev.xdark.clientapi.opengl.GlStateManager.disableBlend
import dev.xdark.clientapi.opengl.GlStateManager.disableTexture2D
import dev.xdark.clientapi.opengl.GlStateManager.enableBlend
import dev.xdark.clientapi.opengl.GlStateManager.enableTexture2D
import dev.xdark.clientapi.opengl.GlStateManager.glLineWidth
import dev.xdark.clientapi.opengl.GlStateManager.translate
import dev.xdark.clientapi.render.DefaultVertexFormats
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.utility.Color
import ru.cristalix.uiengine.utility.V3

object Renderer {

    fun renderBlockFrame(location: V3, color: Color, lineWidth: Float) {
        disableTexture2D()
        disableBlend()
        glLineWidth(lineWidth)
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR)

        val player = clientApi.minecraft().renderViewEntity

        translate(
            -player.lastX - (player.x - player.lastX) * ticks,
            -player.lastY - (player.y - player.lastY) * ticks + 0.0005,
            -player.lastZ - (player.z - player.lastZ) * ticks,
        )

        val tx = location.x
        val ty = location.y
        val tz = location.z
        val alpha = color.alpha.toInt()

        bufferBuilder.apply {
            for (i in 0..1) {
                pos(tx, ty + i, tz).color(color.red, color.green, color.blue, 0).endVertex()
                pos(tx, ty + i, tz + 1.0).color(color.red, color.green, color.blue, alpha).endVertex()
                pos(tx + 1.0, ty + i, tz + 1.0).color(color.red, color.green, color.blue, alpha).endVertex()
                pos(tx + 1.0, ty + i, tz).color(color.red, color.green, color.blue, alpha).endVertex()
                pos(tx, ty + i, tz).color(color.red, color.green, color.blue, alpha).endVertex()
            }

            for (j1 in 0..1) {
                for (l1 in 0..1) {
                    pos(tx + j1.toDouble(), ty, tz + l1.toDouble()).color(color.red, color.green, color.blue, 0)
                        .endVertex()
                    pos(tx + j1.toDouble(), ty, tz + l1.toDouble()).color(color.red, color.green, color.blue, alpha)
                        .endVertex()
                    pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble()).color(color.red, color.green, color.blue, alpha)
                        .endVertex()
                    pos(tx + j1.toDouble(), ty + 1, tz + l1.toDouble()).color(color.red, color.green, color.blue, 0)
                        .endVertex()
                }
            }
        }

        tessellator.draw()
        glLineWidth(lineWidth)
        enableBlend()
        enableTexture2D()
    }
}
