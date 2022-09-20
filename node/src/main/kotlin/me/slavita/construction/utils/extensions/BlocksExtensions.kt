package me.slavita.construction.utils.extensions

import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Location
import org.bukkit.World

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
}