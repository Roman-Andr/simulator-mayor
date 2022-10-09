package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
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

    fun tryPurchase(cost: Long, action: () -> Unit) {
        if (stats.money >= cost) {
            stats.money -= cost
            action()
        } else {
            player.playSound(MusicSound.DENY)
        }
    }

    fun canPurchase(cost: Long): Boolean {
        return stats.money >= cost
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