package me.slavita.construction.mod

import dev.xdark.clientapi.event.render.RenderPass
import dev.xdark.clientapi.opengl.GlStateManager
import dev.xdark.clientapi.render.DefaultVertexFormats
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import org.lwjgl.opengl.GL11
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.utility.*
import java.util.*
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class WorldRectangle(
    val uuid: UUID = UUID.randomUUID(),
    var rgb: RGB,
    var location: V3,
    val radius: Double = 1.3
)

object CellBorders {
    private val places = arrayListOf<WorldRectangle>()
    private val placeCache = hashMapOf<UUID, ArrayList<Triple<Double, Double, Double>>>()

    init {
        mod.registerChannel("rectangle:new") {
            places.add(
                WorldRectangle(
                    UUID.randomUUID(),
                    GlowColor.GREEN,
                    V3(0.0, 0.0, 0.0),
                    2.0
                )
            )
        }

        mod.registerHandler<RenderPass> {
            val minecraft = clientApi.minecraft()
            val entity = minecraft.renderViewEntity

            val pt = minecraft.timer.renderPartialTicks
            val prevX = entity.prevX
            val prevY = entity.prevY
            val prevZ = entity.prevZ

            GlStateManager.disableLighting()
            GlStateManager.disableTexture2D()
            GlStateManager.disableAlpha()
            GlStateManager.shadeModel(GL11.GL_SMOOTH)
            GlStateManager.enableBlend()
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)
            GlStateManager.disableCull()
            GlStateManager.depthMask(false)

            places.sortedByDescending { place ->
                (place.location.x - entity.x).pow(2) + (place.location.z - entity.z).pow(2)
            }.forEach { place ->
                val distance = (place.location.x - entity.x).pow(2) + (place.location.z - entity.z).pow(2)

                if (distance > 100 * 100) return@forEach

                val isInside = distance <= place.radius * place.radius
                if (isInside) return@forEach

                val cache = placeCache[place.uuid] ?: arrayListOf()

                val tessellator = clientApi.tessellator()
                val bufferBuilder = clientApi.tessellator().bufferBuilder

                bufferBuilder.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR)//

                repeat(4 * 2 + 2) { index ->
                    if (cache.size <= index) {
                        cache.add(
                            Triple(
                                place.radius,
                                if (index % 2 == 1) 25.0 else 0.0,
                                place.radius
                            )
                        )
                    }

                    val v3 = cache[index]

                    val x = place.location.x - (entity.x - prevX) * pt - prevX
                    val y = place.location.y - (entity.y - prevY) * pt - prevY
                    val z = place.location.z - (entity.z - prevZ) * pt - prevZ

                    bufferBuilder
                        .pos(x + v3.first, y + v3.second, z + v3.third)
                        .color(place.rgb.red, place.rgb.green, place.rgb.blue, if (index % 2 == 1) 0 else 100)
                        .endVertex()
                }
                tessellator.draw()
            }
            GlStateManager.depthMask(true)
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_CONSTANT_COLOR)
            GlStateManager.shadeModel(GL11.GL_FLAT)
            GlStateManager.enableTexture2D()
            GlStateManager.enableAlpha()
        }
    }
}