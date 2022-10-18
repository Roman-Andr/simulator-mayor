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
    val sourceData: Byte,
    val sourceBlock: Block
) : ItemProperties(type, data) {

    constructor(sourceBlock: Block) : this(
        sourceBlock.location.toPosition(),
        sourceBlock.type,
        (sourceBlock as CraftBlock).nmsBlock.getDropData(sourceBlock.data0).toByte(),
        sourceBlock.data,
        sourceBlock
    )

    fun withOffset(position: Location): StructureBlock {
        return StructureBlock(this.position.add(position), this.type, this.data, this.sourceData, this.sourceBlock)
    }

    fun equalsLocation(location: Location): Boolean {
        return location.blockX == position.x && location.blockY == position.y && location.blockZ == position.z
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as StructureBlock

        if (position != other.position) return false
        if (sourceData != other.sourceData) return false
        if (sourceBlock != other.sourceBlock) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + sourceData
        result = 31 * result + sourceBlock.hashCode()
        return result
    }
}