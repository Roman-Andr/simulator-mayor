package me.slavita.construction.world

import me.slavita.construction.utils.extensions.BlocksExtensions.add
import net.minecraft.server.v1_12_R1.Block
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.EnumProtocol.DirectionData
import net.minecraft.server.v1_12_R1.Item
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Directional

class BlockProperties(
    val position: BlockPosition,
    val type: Material,
    val data: Int,
    val sourceData: Int
) {
    fun withOffset(position: Location): BlockProperties {
        return BlockProperties(this.position.add(position), this.type, this.data, this.sourceData)
    }

    fun equalsItem(item: ItemStack?): Boolean {
        return item != null && item.getType() == type && item.getData().data.toInt() == data
    }

    fun equalsLocation(location: Location): Boolean {
        return location.blockX == position.x && location.blockY == position.y && location.blockZ == position.z
    }
}