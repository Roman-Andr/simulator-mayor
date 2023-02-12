package me.slavita.construction.action.menu.worker

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.action.command.UpgradeWorker
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.WORKER_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.worker.Worker
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class WorkerUpgradeMenu(player: Player, val worker: Worker) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            var infoButton: ReactiveButton
            return choicer {
                title = "${GOLD}${BOLD}Улучшение рабочего"
                description = ""
                info = WORKER_INFO
                storage = mutableListOf(
                    button {
                        item = Icons.get("other", "info1")
                        title = "Информация"
                        hover = worker.toString()
                        hint = ""
                    }.apply { infoButton = this },
                    button {
                        item = Icons.get("other", "add")
                        title = "Улучшить"
                        hover = getUpgradeHover()
                        hint = "Улучшить"
                        backgroundColor = GlowColor.GREEN
                        click { _, _, button ->
                            UpgradeWorker(user, worker).tryExecute()
                            button.hover = getUpgradeHover()
                            infoButton.hover = worker.toString()
                        }
                    },
                    button {
                        item = Icons.get("other", "reload")
                        title = "Продать"
                        hover = getSellHover()
                        hint = "Продать"
                        backgroundColor = GlowColor.RED
                        click { _, _, button ->
                            WorkerSellConfirm(player, worker).tryExecute()
                            button.hover = getSellHover()
                            infoButton.hover = worker.toString()
                        }
                    }
                )
            }
        }
    }

    private fun getUpgradeHover(): String {
        return "${AQUA}Улучшить ${GREEN}${worker.level}$AQUA->${GREEN}${worker.level + 1} ${AQUA}за ${worker.upgradePrice.toMoneyIcon()}"
    }

    private fun getSellHover(): String {
        return "${AQUA}Продать за ${GREEN}${worker.sellPrice.toMoneyIcon()}"
    }
}
