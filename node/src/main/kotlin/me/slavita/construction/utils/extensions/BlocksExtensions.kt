package me.slavita.construction.utils.extensions

import me.slavita.construction.world.BlockProperties
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World

object BlocksExtensions {
    fun BlockPosition.add(position: Location): BlockPosition {
        return this.add(position.x, position.y, position.z)
    }

    fun BlockPosition.toLocation(world: World): Location {
        return Location(world, this.x.toDouble(), this.y.toDouble(), this.z.toDouble())
    }

    fun BlockProperties.equalsLocation(location: Location): Boolean {
        return location.blockX == position.x &&
                location.blockY == position.y &&
                location.blockZ == position.z
    }

    operator fun BlockPosition.minus(additionalPosition: Location): BlockPosition {
        return BlockPosition(this.x - additionalPosition.x,
            this.y - additionalPosition.y,
            this.z - additionalPosition.z)
    }
}