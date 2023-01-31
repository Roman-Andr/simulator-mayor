package me.slavita.construction.mod

import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.opengl.GlStateManager.*
import dev.xdark.clientapi.render.DefaultVertexFormats
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.mod.utils.*
import org.lwjgl.opengl.GL11
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.utility.*
import java.util.*
import kotlin.math.pow

class Border(
    var color: RGB,
    val width: Double,
    val height: Double,
    val location: V3,
) {
    val vertices = arrayListOf<ArrayList<V3>>()

    init {
        arrayOf(-1, 1).forEach { direction ->
            val offset = direction * width / 2.0

            arrayOf(0, 1).forEach { side ->
                val sideVertices = arrayListOf<V3>()
                arrayOf(
                    Pair(0, 0),
                    Pair(0, 1),
                    Pair(1, 0),
                    Pair(1, 1),
                ).forEach { (xz, y) ->
                    sideVertices.add(
                        V3(
                            direction * side * xz * width - offset,
                            y * height,
                            direction * (side xor 1) * xz * width - offset
                        )
                    )
                }
                vertices.add(sideVertices)
            }
        }
    }
}

object CellBorders : IRegistrable {
    private val borders = arrayListOf<Border>()

    override fun register() {
        mod.registerChannel("rectangle:new") {
            borders.add(
                Border(
                    GlowColor.GREEN,
                    25.0,
                    50.0,
                    V3(0.0, 0.0, 0.0)
                )
            )
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

                val isInside = distance <= border.width.pow(2)
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
                                .color(color.red, color.green, color.blue, if (index % 2 == 1) 0 else 100)
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
