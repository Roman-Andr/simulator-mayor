package me.slavita.construction.mod

import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.opengl.GlStateManager.*
import dev.xdark.clientapi.render.DefaultVertexFormats
import me.slavita.construction.common.utils.*
import me.slavita.construction.mod.utils.*
import org.lwjgl.opengl.GL11
import ru.cristalix.uiengine.UIEngine.clientApi
import kotlin.math.pow

object CellBorders : IRegistrable {
    private val borders = hashSetOf<Border>()

    override fun register() {
        mod.registerChannel(CREATE_BORDER_CHANNEL) {
            val uuid = readUuid()
            if (borders.find { it.uuid == uuid } != null) clientApi.chat().sendChatMessage("DUPLICATE!")
            borders.add(
                Border(
                    uuid,
                    readRgb(),
                    readInt(),
                    readDouble(),
                    readDouble(),
                    readV3()
                )
            )
        }

        mod.registerChannel(CHANGE_BORDER_CHANNEL) {
            borders.find { it.uuid == readUuid() }?.apply {
                color = readRgb()
            }
        }

        mod.registerChannel(DELETE_BORDER_CHANNEL) {
            borders.removeIf { it.uuid == readUuid() }
        }

        mod.registerHandler<RenderPass> {
            disableLighting()
            disableTexture2D()
            disableAlpha()
            shadeModel(GL11.GL_SMOOTH)
            enableBlend()
            blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)
            disableCull()
            depthMask(false)

            borders.forEach { border ->
                val distance = (border.location.x - entity.x).pow(2) + (border.location.z - entity.z).pow(2)

                val isInside = distance <= (border.width / 2).pow(2)
                if (isInside) return@forEach

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
}
