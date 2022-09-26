package me.slavita.construction.world

import me.slavita.construction.utils.extensions.BlocksExtensions.add
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Colorable

val colorableMaterials = arrayOf(
    Material.WOOD,
    Material.CONCRETE,
    Material.CONCRETE_POWDER,
    Material.STAINED_CLAY,
    Material.HARD_CLAY,
    Material.WOOL,
    Material.GLASS,
    Material.STAINED_GLASS_PANE,
    Material.STAINED_GLASS,
    Material.WOOD,
)

class BlockProperties(
    val position: BlockPosition,
    val type: Material,
    val data: Byte
) {
    val colorable: Boolean

    init {
        colorable = colorableMaterials.contains(type)
    }

    fun withOffset(position: Location): BlockProperties {
        return BlockProperties(this.position.add(position), this.type, this.data)
    }

    fun equalsItem(item: ItemStack?): Boolean {
        if (item == null || item.getType() != type) return false
        if (colorable) return item.getData().data == data
        return true
    }

    fun equalsLocation(location: Location): Boolean {
        return location.blockX == position.x && location.blockY == position.y && location.blockZ == position.z
    }
}