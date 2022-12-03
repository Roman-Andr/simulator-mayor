package me.slavita.construction.banner

import me.func.mod.world.Banners
import me.func.mod.world.Banners.location
import me.func.protocol.data.color.RGB
import me.func.protocol.data.color.Tricolor
import me.func.protocol.data.element.Banner
import me.func.protocol.data.element.MotionType
import me.func.world.Label
import me.slavita.construction.utils.extensions.BlocksExtensions.toYaw
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector

object BannerUtil {
    private const val offset = 0.52

    fun loadBanner(banner: Map<*, *>, label: Label, withPitch: Boolean = false, opacity: Double = 0.45) {
        Banners.new(
            Banner.builder()
                .weight(banner["weight"] as Int)
                .height(banner["height"] as Int)
                .content(banner["content"] as String)
                .carveSize(banner["carve-size"] as Double)
                .opacity(opacity)
                .x(label.toCenterLocation().x)
                .y(label.y + banner["offset"] as Double)
                .z(label.toCenterLocation().z)
                .apply {
                    if (withPitch) {
                        watchingOnPlayer(true)
                    } else {
                        watchingOnPlayerWithoutPitch(true)
                    }
                    (banner["lines-size"] as List<*>).forEachIndexed { index, value ->
                        this.resizeLine(index, value as Double)
                    }
                }
                .build()
        )
    }

    fun createFloorBanner(location: Location, color: RGB): Banner {
        return create(
            BannerInfo(
                location,
                BlockFace.UP,
                listOf(),
                16 * 23,
                16 * 23,
                color,
                0.24,
                MotionType.CONSTANT,
                -90.0F
            )
        )
    }

    fun createDual(info: BannerInfo): Pair<Banner, Banner> {
        info.run {
            return Pair(
                create(BannerInfo(source, blockFace, content, width, height, color, opacity, motionType, pitch)),
                create(
                    BannerInfo(
                        Location(
                            source.world,
                            source.x + blockFace.modX,
                            source.y,
                            source.z + blockFace.modZ
                        ), blockFace.oppositeFace, content, width, height, color, opacity, motionType, pitch
                    )
                )
            )
        }
    }

    fun createRectangle(center: Location, radius: Double, color: Tricolor, width: Int, height: Int): HashSet<Banner> {
        val banners = hashSetOf<Banner>()
        listOf(BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.EAST).forEach {
            banners.addAll(
                createDual(
                    BannerInfo(
                        center.clone().add(it.modX * radius, 0.0, it.modZ * radius),
                        it,
                        listOf(),
                        width * 16,
                        height * 16,
                        color,
                        0.25
                    )
                ).toList()
            )
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
                .xray(0.0)
                .carveSize(carveSize)
                .apply {
                    content(content.joinToString("\n") { it.first })
                    content.forEachIndexed { index, value ->
                        resizeLine(index, value.second)
                    }
                }
                .watchingOnPlayer(watchingOnPlayer)
                .build()
                .apply {
                    location(
                        source.clone().add(Vector(offset * blockFace.modX, -0.5 + height / 16, offset * blockFace.modZ))
                            .apply {
                                setYaw(blockFace.toYaw())
                                setPitch(info.pitch)
                            }
                    )
                }
        }
    }
}