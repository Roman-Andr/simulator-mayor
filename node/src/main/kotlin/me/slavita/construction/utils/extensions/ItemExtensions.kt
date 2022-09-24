package me.slavita.construction.utils.extensions

import me.slavita.construction.world.BlockProperties
import org.bukkit.inventory.ItemStack

object ItemExtensions {
    fun ItemStack.equalsData(blockData: BlockProperties): Boolean {
        return (getType() == blockData.type && getData().data == blockData.data)
    }
}