package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.worker.Worker
import org.bukkit.Location
import org.bukkit.entity.Player

class User(
    var player: Player,
    var stats: Statistics
) {
    var workers = hashSetOf<Worker>()
    var activeProjects = hashSetOf<Project>()
    var watchableProject: Project? = null

    fun getEmptyPlace(): Location? {
        val used = arrayListOf<Location>()

        activeProjects.forEach {
            used.add(it.structure.allocation.toBlockLocation())
        }

        app.mainWorld.map.labels("place").forEach {
            if (!used.contains(it.toBlockLocation())) return it
        }
        return null
    }
}