package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.worker.Worker
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

class User(
    var player: Player,
    var stats: Statistics
) {
    var workers = hashSetOf<Worker>()
    var activeProjects = hashSetOf<Project>()
    var watchableProject: Project? = null

    init {
        Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
            println(player.location)
            if (watchableProject != null && !watchableProject!!.structure.structure.box.contains(player.location)) {
                watchableProject!!.structure.hide()
                println("hide: ${watchableProject!!.structure.structure.box}")
                watchableProject = null
            }
            if (watchableProject == null) {
                activeProjects.forEach {
                    if (it.structure.structure.box.contains(player.location)) {
                        watchableProject = it
                        println("show: ${it.structure.structure.box}")
                        it.structure.show()
                        return@forEach
                    }
                }
            }
        }, 0, 5)
    }

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