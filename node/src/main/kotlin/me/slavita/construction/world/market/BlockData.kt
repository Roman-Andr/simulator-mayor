package me.slavita.construction.world.market

import org.bukkit.Material

data class BlockData(
    val name: String,
    val type: Material,
    val data: Byte
)