package me.slavita.construction.player

import clepto.bukkit.event.PlayerWrapper
import me.slavita.construction.worker.Worker
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class User(
    var player: Player,
    var stats: Statistics
) : PlayerWrapper {
    var workers = mutableListOf<Worker>()

    override fun getPlayer(): CraftPlayer {
        return player as CraftPlayer
    }

}