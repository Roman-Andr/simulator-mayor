package me.slavita.construction.world

import me.slavita.construction.utils.extensions.BlocksExtensions.add
import me.slavita.construction.utils.extensions.BlocksExtensions.toPosition
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlock

class StructureBlock(
    val position: BlockPosition,
    type: Material,
    data: Byte,
    val sourceData: Byte
) : ItemProperties(type, data) {

    companion object {
        fun fromBlock(block: Block) = StructureBlock(
            block.location.toPosition(),
            block.type,
            (block as CraftBlock).nmsBlock.getDropData(block.data0).toByte(),
            block.data
        )
    }

    fun withOffset(position: Location): StructureBlock {
        return StructureBlock(this.position.add(position), this.type, this.data, this.sourceData)
    }

    fun equalsLocation(location: Location): Boolean {
        return location.blockX == position.x && location.blockY == position.y && location.blockZ == position.z
    }
}