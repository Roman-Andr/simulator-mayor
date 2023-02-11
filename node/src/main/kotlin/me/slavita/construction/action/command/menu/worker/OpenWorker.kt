package me.slavita.construction.action.command.menu.worker

import me.func.mod.Anime
import me.func.mod.conversation.data.LootDrop
import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.User
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.worker.Worker

@Suppress("DEPRECATION")
class OpenWorker(override val user: User, vararg val workers: Worker) : CooldownCommand(user, 2) {
    override fun execute() {
        Anime.openLootBox(
            user.player,
            *(workers.map { LootDrop(it.rarity.getIcon(), it.toString(), it.rarity.dropRare) }).toTypedArray()
        )
        user.data.workers.addAll(workers)
        user.updateAchievement(AchievementType.WORKERS)
    }
}
