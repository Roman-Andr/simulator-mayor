package me.slavita.construction.action.command

import me.func.mod.Anime
import me.func.mod.conversation.data.LootDrop
import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.User
import me.slavita.construction.worker.Worker
import org.bukkit.entity.Player

class OpenWorker(val user: User, val worker: Worker, val lootDrop: LootDrop) : CooldownCommand(user.player, 2) {
    override fun execute() {
        Anime.openLootBox(player, lootDrop)
        user.workers.add(worker)
    }
}