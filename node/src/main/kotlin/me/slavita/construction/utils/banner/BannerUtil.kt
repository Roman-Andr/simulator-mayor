package me.slavita.construction.utils.banner

import me.func.mod.world.Banners.location
import me.func.protocol.data.element.Banner
import me.slavita.construction.utils.extensions.BlocksExtensions.toYaw
import org.bukkit.Location
import org.bukkit.util.Vector

object BannerUtil {
    private const val offset = 0.52

    fun createDual(info: BannerInfo): Pair<Banner, Banner> {
        info.run {
            return Pair(
                create(BannerInfo(source, blockFace, content, width, height, color, opacity, motionType, pitch)),
                create(BannerInfo(
                    Location(
                        source.world,
                        source.x + blockFace.modX * 0.5,
                        source.y,
                        source.z + blockFace.modZ * 0.5
                    ), blockFace.oppositeFace, content, width, height, color, opacity, motionType, pitch
                ))
            )
        }
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
                        source.add(Vector(offset*blockFace.modX, 0.0, offset*blockFace.modZ)).apply {
                            setYaw(blockFace.toYaw())
                            setPitch(0.0f)
                        }
                    )
                }
        }
    }
}