package me.slavita.construction.world

import dev.implario.bukkit.world.V3
import me.slavita.construction.utils.V3i
import org.bukkit.Location
import org.bukkit.block.Block

class Box(val min: Location, val max: Location) {
    val dimensions = V3i(max.blockX - min.blockX + 1, max.blockY - min.blockY + 1, max.blockZ - min.blockZ + 1)

    fun forEachBukkit(action: (Block) -> Unit) {
        (min.blockX..max.blockX).forEach { x ->
            (min.blockY..max.blockY).forEach { y ->
                (min.blockZ..max.blockZ).forEach { z ->
                    action(Location(min.world, x.toDouble(), y.toDouble(), z.toDouble()).block)
                }
            }
        }
    }

    fun contains(location: Location): Boolean {
        return min.getX() <= location.getX() && max.getX() >= location.getX() &&
                min.getY() <= location.getY() && max.getY() >= location.getY() &&
                min.getZ() <= location.getZ() && max.getZ() >= location.getZ()
    }

    fun contains(location: Location, offset: Location): Boolean {
        return contains(location.clone().add(-offset.x, -offset.y, -offset.z).add(min.x, min.y, min.z))
    }
}