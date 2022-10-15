package me.slavita.construction.world

import me.slavita.construction.utils.V3i
import me.slavita.construction.utils.extensions.BlocksExtensions.withOffset
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Location
import org.bukkit.block.Block

class Box(val min: Location, val max: Location) {
    val dimensions = V3i(max.blockX - min.blockX + 1, max.blockY - min.blockY + 1, max.blockZ - min.blockZ + 1)
    val center = Location(min.world, (max.blockX + min.blockX) / 2.0, (max.blockY + min.blockY) / 2.0, (max.blockZ + min.blockZ) / 2.0).toCenterLocation()!!
    val bottomCenter = Location(min.world, center.x, min.y, center.z).toCenterLocation()!!

    fun forEachBukkit(action: (Block) -> Unit) {
        (min.blockX..max.blockX).forEach { x ->
            (min.blockY..max.blockY).forEach { y ->
                (min.blockZ..max.blockZ).forEach { z ->
                    action(Location(min.world, x.toDouble(), y.toDouble(), z.toDouble()).block)
                }
            }
        }
    }

    fun withOffset(location: Location): Box {
        return Box(min.withOffset(location), max.withOffset(location))
    }

    fun contains(location: Location): Boolean {
        return min.getX() <= location.getX() && max.getX() >= location.getX() &&
                min.getY() <= location.getY() && max.getY() >= location.getY() &&
                min.getZ() <= location.getZ() && max.getZ() >= location.getZ()
    }

    fun contains(location: BlockPosition): Boolean {
        return min.getX() <= location.x && max.getX() >= location.x &&
                min.getY() <= location.y && max.getY() >= location.y &&
                min.getZ() <= location.z && max.getZ() >= location.z
    }

    override fun toString(): String {
        return "Box(min: $min, max: $max)"
    }
}