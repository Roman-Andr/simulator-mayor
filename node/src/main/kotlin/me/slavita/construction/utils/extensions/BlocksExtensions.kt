package me.slavita.construction.utils.extensions

import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.BlockFace

object BlocksExtensions {
    fun BlockPosition.add(position: Location): BlockPosition {
        return this.add(position.x, position.y, position.z)
    }

    fun BlockPosition.toLocation(world: World): Location {
        return Location(world, this.x.toDouble(), this.y.toDouble(), this.z.toDouble())
    }

    operator fun BlockPosition.minus(additionalPosition: Location): BlockPosition {
        return BlockPosition(this.x - additionalPosition.x,
            this.y - additionalPosition.y,
            this.z - additionalPosition.z)
    }

    fun Location.withOffset(offset: Location): Location {
        return Location(offset.world, x + offset.x, y + offset.y, z + offset.z)
    }

    fun BlockFace.toYaw(): Float {
        return when (this) {
            BlockFace.EAST -> -90
            BlockFace.WEST -> 90
            BlockFace.SOUTH -> 0
            BlockFace.NORTH -> 180
            else -> 0
        }.toFloat()
    }
}