package me.slavita.construction.player

import me.slavita.construction.project.Project
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.worker.Worker
import org.bukkit.entity.Player

class User(
    var player: Player,
    var stats: Statistics
) {
    var workers = hashSetOf<Worker>()
    var activeProjects = hashSetOf<Project>()
    var watchableProject: Project? = null

    fun tryPurchase(cost: Long, acceptAction: () -> Unit, denyAction: () -> Unit = { player.playSound(MusicSound.DENY) }) {
        if (stats.money >= cost) {
            stats.money -= cost
            acceptAction()
        } else {
            denyAction()
        }
    }

    fun canPurchase(cost: Long): Boolean {
        return stats.money >= cost
    }
}