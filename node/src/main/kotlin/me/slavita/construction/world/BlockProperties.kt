package me.slavita.construction.world

import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Material

data class BlockProperties(
    val nextBlock: BlockProperties?,
    val position: BlockPosition,
    val type: Material,
    val data: Byte
)