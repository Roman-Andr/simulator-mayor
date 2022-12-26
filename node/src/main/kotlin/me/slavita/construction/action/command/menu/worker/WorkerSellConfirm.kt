package me.slavita.construction.action.command.menu.worker

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.user
import me.slavita.construction.worker.Worker
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player

class WorkerSellConfirm(player: Player, val worker: Worker) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Confirmation(
                text = listOf(
                    "${AQUA}Продать строителя",
                    worker.name,
                    "${AQUA}за ${WHITE}${worker.sellPrice.toMoneyIcon()}",
                )
            ) {
                this@user.data.workers.remove(worker)
                this@user.data.statistics.money += worker.sellPrice
                WorkerTeamMenu(player).tryExecute()
            }
        }
    }
}