package me.slavita.construction.action.command.menu.worker

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.action.command.UpgradeWorker
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.worker.Worker
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player
import java.util.stream.Collectors
import java.util.stream.Stream

class WorkerUpgradeMenu(player: Player, val worker: Worker) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            var infoButton = ReactiveButton()
            return Selection(
                title = "Улучшение рабочего",
                vault = Emoji.DOLLAR,
                money = "Ваш баланс ${stats.money.toMoney()}",
                rows = 4,
                columns = 3,
                storage = mutableListOf(
                    ReactiveButton()
                        .item(ItemIcons.get("other", "info1"))
                        .title("Информация")
                        .hover(getInfoHover())
                        .hint("Информация")
                        .apply {
                            infoButton = this
                        },
                    ReactiveButton()
                        .item(ItemIcons.get("other", "add"))
                        .title("Улучшить")
                        .hover(getUpgradeHover())
                        .hint("Улучшить")
                        .onClick { _, _, button ->
                            UpgradeWorker(player, worker).tryExecute()
                            button.hover = getUpgradeHover()
                            infoButton.hover = getInfoHover()
                        },
                    ReactiveButton()
                        .item(ItemIcons.get("other", "reload"))
                        .title("Продать")
                        .hover(getSellHover())
                        .hint("Продать")
                        .onClick { _, _, button ->
                            WorkerSellConfirm(player, worker).tryExecute()
                            button.hover = getSellHover()
                            infoButton.hover = getInfoHover()
                        }
                )
            )
        }
    }

    private fun getInfoHover(): String {
        return Stream.of(
            "${AQUA}Имя: ${worker.name}\n",
            "${AQUA}Редкость: ${worker.rarity.title}\n",
            "${AQUA}Уровень: ${worker.level}${WHITE}${Emoji.UP}\n",
            "${AQUA}Надёжность: ${worker.reliability}\n",
            "${AQUA}Жадность: ${worker.rapacity.title}"
        ).collect(Collectors.joining())
    }

    private fun getUpgradeHover(): String {
        return "${AQUA}Улучшить ${GREEN}${worker.level}${AQUA}->${GREEN}${worker.level + 1} ${AQUA}за ${worker.upgradePrice.toMoneyIcon()}"
    }

    private fun getSellHover(): String {
        return "${AQUA}Продать за ${GREEN}${worker.sellPrice.toMoneyIcon()}"
    }
}