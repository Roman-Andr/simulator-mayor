package me.slavita.construction.action.command.menu.worker

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.user
import me.slavita.construction.worker.Worker
import org.bukkit.entity.Player

class WorkerSellConfirm(player: Player, val worker: Worker) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Confirmation(
                text = listOf(
                    "Продать строителя",
                    worker.name,
                    "за ${worker.sellPrice.toMoneyIcon()}",
                )
            ) {
                this@user.stats.workers.remove(worker)
                this@user.stats.money += worker.sellPrice
                WorkerTeamMenu(player).tryExecute()
            }
        }
    }
}