package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.worker.Worker
import me.slavita.construction.utils.extensions.KotlinExtensions.listOfField
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.entity.Player

class User(
    var player: Player,
    var stats: Statistics
) {
    var workers = hashSetOf<Worker>()
    var activeProjects = hashSetOf<Project>()

    fun getEmptyPlace(): BlockPosition? {
        val used = activeProjects.listOfField(Project::place)
        app.mainWorld.map.labels("place").forEach {
            val position = BlockPosition(it.blockX, it.blockY, it.blockZ)
            if (!used.contains(position)) return position
        }
        return null
    }
}