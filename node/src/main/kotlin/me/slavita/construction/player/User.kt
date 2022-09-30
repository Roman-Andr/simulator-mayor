package me.slavita.construction.player

import me.slavita.construction.project.Project
import me.slavita.construction.worker.Worker
import org.bukkit.entity.Player

class User(
    var player: Player,
    var stats: Statistics
) {
    var workers = hashSetOf<Worker>()
    var activeProjects = hashSetOf<Project>()
}