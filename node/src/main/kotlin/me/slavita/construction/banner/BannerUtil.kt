package me.slavita.construction.banner

import me.func.mod.world.Banners.location
import me.func.protocol.data.color.Tricolor
import me.func.protocol.data.element.Banner
import me.slavita.construction.utils.extensions.BlocksExtensions.toYaw
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector

object BannerUtil {
	private const val offset = 0.52

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

	fun create(info: BannerInfo): Banner {
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