package me.slavita.construction.player

import clepto.bukkit.event.PlayerWrapper
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.Structure
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import ru.cristalix.core.math.V3

class User(
    var player: Player,
    var stats: Statistics
) : PlayerWrapper {
    var workers = mutableListOf<Worker>()
    var currentStructure: Structure? = null

    override fun getPlayer(): CraftPlayer {
        return player as CraftPlayer
    }
}