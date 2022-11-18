package me.slavita.construction.utils.extensions

import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.BlockFace
import ru.cristalix.core.math.V3

object BlocksExtensions {
    fun BlockPosition.add(position: Location): BlockPosition {
        return this.add(position.x, position.y, position.z)
    }

    fun BlockPosition.toLocation(world: World): Location {
        return Location(world, this.x.toDouble(), this.y.toDouble(), this.z.toDouble())
    }

    operator fun BlockPosition.minus(additionalPosition: Location): BlockPosition {
        return BlockPosition(
            this.x - additionalPosition.x,
            this.y - additionalPosition.y,
            this.z - additionalPosition.z
        )
    }

    fun Location.withOffset(offset: Location): Location {
        return Location(offset.world, x + offset.x, y + offset.y, z + offset.z)
    }

    fun Location.toPosition(): BlockPosition {
        return BlockPosition(x, y, z)
    }

    fun Location.toV3(): V3 {
        return V3(x, y, z)
    }

    operator fun Location.unaryMinus(): Location {
        return Location(world, -x, -y, -z, yaw, pitch)
    }

    fun BlockFace.toYaw(): Float {
        return when (this) {
            BlockFace.EAST  -> -90
            BlockFace.WEST  -> 90
            BlockFace.SOUTH -> 0
            BlockFace.NORTH -> 180
            BlockFace.NORTH_WEST -> 135
            BlockFace.NORTH_EAST -> -135
            BlockFace.SOUTH_WEST -> 45
            BlockFace.SOUTH_EAST -> -45
            else            -> 0
        }.toFloat()
    }
}