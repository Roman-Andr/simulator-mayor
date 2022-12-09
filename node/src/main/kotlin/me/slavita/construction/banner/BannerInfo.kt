package me.slavita.construction.banner

import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import me.func.protocol.data.element.MotionType
import org.bukkit.Location
import org.bukkit.block.BlockFace

class BannerInfo(
    val source: Location,
    val blockFace: BlockFace,
    val content: List<Pair<String, Double>> = listOf(),
    val width: Int = 16,
    val height: Int = 16,
    val color: RGB = GlowColor.GREEN,
    val opacity: Double = 0.65,
    val motionType: MotionType = MotionType.CONSTANT,
    val pitch: Float = 0.0F,
    val watchingOnPlayer: Boolean = false,
    val carveSize: Double = 2.0,
)