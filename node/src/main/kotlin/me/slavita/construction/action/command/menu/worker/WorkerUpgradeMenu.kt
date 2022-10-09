package me.slavita.construction.action.command.menu.worker

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.UpgradeWorker
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.worker.Worker
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class WorkerUpgradeMenu(player: Player, val worker: Worker) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            var infoButton: ReactiveButton
            return get(MenuInfo("Улучшение рабочего", StatsType.MONEY, 4, 3)).apply {
                storage = mutableListOf(
                    button {
                        item = ItemIcons.get("other", "info1")
                        title = "Информация"
                        hover = worker.toString()
                        hint = "Информация"
                    }.apply { infoButton = this },
                    button {
                        item = ItemIcons.get("other", "add")
                        title = "Улучшить"
                        hover = getUpgradeHover()
                        hint = "Улучшить"
                        onClick { _, _, button ->
                            UpgradeWorker(player, worker).tryExecute()
                            button.hover = getUpgradeHover()
                            infoButton.hover = worker.toString()
                        }},
                    button {
                        item = ItemIcons.get("other", "reload")
                        title = "Продать"
                        hover = getSellHover()
                        hint = "Продать"
                        onClick { _, _, button ->
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
        return "${AQUA}Улучшить ${GREEN}${worker.level}${AQUA}->${GREEN}${worker.level + 1} ${AQUA}за ${worker.upgradePrice.toMoneyIcon()}"
    }

    private fun getSellHover(): String {
        return "${AQUA}Продать за ${GREEN}${worker.sellPrice.toMoneyIcon()}"
    }
}