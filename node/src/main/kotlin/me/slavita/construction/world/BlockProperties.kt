package me.slavita.construction.world

import me.slavita.construction.utils.extensions.BlocksExtensions.add
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Colorable

class BlockProperties(
    val position: BlockPosition,
    val type: Material,
    data: Byte
) {
    val data: Byte

    init {
        this.data = if (isColorable()) data else 0
    }

    fun withOffset(position: Location): BlockProperties {
        return BlockProperties(this.position.add(position), this.type, this.data)
    }

    private fun isColorable(): Boolean {
        return (type == Material.CONCRETE ||
                type == Material.CONCRETE_POWDER ||
                type == Material.WOOL ||
                type == Material.WOOD)
    }
}