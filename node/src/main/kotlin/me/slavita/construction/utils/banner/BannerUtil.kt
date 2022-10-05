package me.slavita.construction.utils.banner

import me.func.mod.world.Banners.location
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.element.Banner
import me.slavita.construction.utils.extensions.BlocksExtensions.toYaw
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector

object BannerUtil {
    private const val offset = 0.52

    private fun createDual(info: BannerInfo): Pair<Banner, Banner> {
        info.run {
            return Pair(
                create(BannerInfo(source, blockFace, content, width, height, color, opacity, motionType, pitch)),
                create(BannerInfo(
                    Location(
                        source.world,
                        source.x + blockFace.modX,
                        source.y,
                        source.z + blockFace.modZ
                    ), blockFace.oppositeFace, content, width, height, color, opacity, motionType, pitch
                ))
            )
        }
    }

    fun createRectangle(center: Location, side: Double): List<Banner> {
        val banners = mutableListOf<Banner>()
        listOf(BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.EAST).forEach {
            banners.addAll(createDual(BannerInfo(
                center.clone().add(Vector(it.modX*side, 0.0, it.modZ*side)),
                it,
                listOf(),
                (side*2+1).toInt()*16,
                16*20,
                GlowColor.GREEN,
                0.25
            )).toList())
        }
        return banners
    }

    private fun create(info: BannerInfo): Banner {
        info.run {
            return Banner.builder()
                .motionType(motionType)
                .weight(width)
                .height(height)
                .opacity(opacity)
                .red(color.red)
                .green(color.green)
                .blue(color.blue)
                .apply {
                    content(content.joinToString("\n") { it.first })
                    content.forEachIndexed { index, value ->
                        resizeLine(index, value.second)
                    }
                }
                .build()
                .apply {
                    location(
                        source.clone().add(Vector(offset*blockFace.modX, -0.5 + height/16, offset*blockFace.modZ)).apply {
                            setYaw(blockFace.toYaw())
                            setPitch(0.0f)
                        }
                    )
                }
        }
    }
}