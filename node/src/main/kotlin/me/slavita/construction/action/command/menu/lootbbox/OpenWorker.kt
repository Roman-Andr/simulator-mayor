package me.slavita.construction.action.command.menu.lootbbox

import me.func.mod.Anime
import me.func.mod.conversation.data.LootDrop
import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.User
import me.slavita.construction.worker.Worker

class OpenWorker(val user: User, vararg val workers: Worker) : CooldownCommand(user.player, 2) {
    override fun execute() {
        Anime.openLootBox(
            player,
            *(workers.map { LootDrop(it.rarity.getIcon(), it.toString(), it.rarity.dropRare) }).toTypedArray()
        )
        user.data.workers.addAll(workers)
    }
}