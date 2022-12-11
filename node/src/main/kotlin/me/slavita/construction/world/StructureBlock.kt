package me.slavita.construction.world

import me.slavita.construction.utils.BlocksExtensions.add
import me.slavita.construction.utils.BlocksExtensions.toPosition
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.IBlockData
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlock

class StructureBlock(
    val position: BlockPosition,
    type: Material,
    data: Byte,
    val sourceData: Byte,
    val sourceCraftData: IBlockData,
) : ItemProperties(type, data) {

    constructor(sourceBlock: Block) : this(
        sourceBlock.location.toPosition(),
        sourceBlock.type,
        (sourceBlock as CraftBlock).nmsBlock.getDropData(sourceBlock.data0).toByte(),
        sourceBlock.data,
        sourceBlock.data0
    )

    fun withOffset(position: Location): StructureBlock {
        return StructureBlock(this.position.add(position), this.type, this.data, this.sourceData, this.sourceCraftData)
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

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + sourceData
        return result
    }
}