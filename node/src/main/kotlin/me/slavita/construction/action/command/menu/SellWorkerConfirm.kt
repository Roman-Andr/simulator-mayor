package me.slavita.construction.action.command.menu

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.worker.Worker
import org.bukkit.entity.Player

class SellWorkerConfirm(player: Player, val worker: Worker) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Confirmation( text = listOf(
                "Продать строителя",
                worker.name,
                "за ${worker.sellPrice}${Emoji.DOLLAR}",
            )) { player ->
                app.getUser(player).workers.remove(worker)
            }
        }
    }
}