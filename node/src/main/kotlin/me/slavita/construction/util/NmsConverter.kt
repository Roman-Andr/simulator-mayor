package me.slavita.construction.util

import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlock

object NmsConverter {
    fun getNmsCopy(block: Block): net.minecraft.server.v1_12_R1.Block {
        return (block as CraftBlock).nmsBlock
    }
}