package me.slavita.construction.world

import me.slavita.construction.utils.extensions.BlocksExtensions.add
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Location
import org.bukkit.Material

class BlockProperties(
    val position: BlockPosition,
    val type: Material,
    val data: Byte
) {
    fun withOffset(position: Location): BlockProperties {
        return BlockProperties(this.position.add(position), this.type, this.data)
    }
}