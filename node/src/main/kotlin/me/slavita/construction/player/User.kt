package me.slavita.construction.player

import me.slavita.construction.worker.Worker
import org.bukkit.entity.Player

class User(
    var player: Player,
    var stats: Statistics
) {
    var workers = mutableListOf<Worker>()

}