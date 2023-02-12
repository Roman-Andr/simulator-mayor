package me.slavita.construction.reward

import me.slavita.construction.action.menu.worker.OpenWorker
import me.slavita.construction.player.User
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.ChatColor.AQUA

class WorkerReward(private val rarity: WorkerRarity) : Reward {
    override fun getReward(user: User) {
        OpenWorker(user, WorkerGenerator.generate(rarity))
    }

    override fun toString() = "${rarity.title} ${AQUA}Рабочий"
}
