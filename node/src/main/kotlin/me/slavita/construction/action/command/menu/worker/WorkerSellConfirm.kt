package me.slavita.construction.action.command.menu.worker

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.worker.Worker
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player

class WorkerSellConfirm(player: Player, val worker: Worker) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return Confirmation(
                text = listOf(
                    "${AQUA}Продать строителя",
                    worker.name,
                    "${AQUA}за ${WHITE}${worker.sellPrice.toMoneyIcon()}",
                )
            ) { player ->
                data.workers.remove(worker)
                data.addMoney(worker.sellPrice)
                WorkerTeamMenu(player).tryExecute()
            }
        }
    }
}
