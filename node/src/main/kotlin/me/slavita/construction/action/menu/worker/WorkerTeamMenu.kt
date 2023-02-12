package me.slavita.construction.action.menu.worker

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.WORKER_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.getVault
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.size
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class WorkerTeamMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${GREEN}${BOLD}Ваши работники"
                getVault(user, StatsType.MONEY)
                size(4, 4)
                info = WORKER_INFO
                storage = this@user.data.workers.sortedByDescending { it.rarity }.mapM { worker ->
                    button {
                        item = Icons.get(
                            worker.rarity.iconKey,
                            worker.rarity.iconValue,
                            false,
                            worker.rarity.iconMaterial
                        )
                        title = worker.name
                        hover = worker.toString()
                        hint = "Выбрать"
                        click { _, _, _ ->
                            WorkerUpgradeMenu(player, worker).keepHistory().tryExecute()
                        }
                    }
                }
            }
        }
    }
}
