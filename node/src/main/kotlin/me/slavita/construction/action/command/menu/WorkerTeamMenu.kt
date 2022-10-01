package me.slavita.construction.action.command.menu

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.utils.Formatter.toMoney
import me.slavita.construction.utils.Formatter.toMoneyIcon
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player
import java.util.stream.Collectors
import java.util.stream.Stream

class WorkerTeamMenu(player: Player) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = "Ваши строители",
                vault = Emoji.DOLLAR,
                money = "Ваш баланс ${stats.money.toMoney()}",
                rows = 4,
                columns = 5,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    workers.forEach { worker ->
                        this@storage.add(
                            ReactiveButton()
                                .item(ItemIcons.get(worker.rarity.iconKey, worker.rarity.iconValue, worker.rarity.iconMaterial))
                                .title(worker.name)
                                .hover(Stream.of(
                                    "${AQUA}Имя: ${worker.name}\n",
                                    "${AQUA}Редкость: ${worker.rarity.title}\n",
                                    "${AQUA}Уровень: ${worker.skill}${WHITE}${Emoji.UP}\n",
                                    "${AQUA}Надёжность: ${worker.reliability}\n",
                                    "${AQUA}Жадность: ${worker.rapacity.title}\n",
                                    "${AQUA}Продать за ${worker.sellPrice.toMoneyIcon()}"
                                ).collect(Collectors.joining()))
                                .hint("${AQUA}Продать")
                                .onClick { _, _, _ ->
                                    SellWorkerConfirm(player, worker).tryExecute()
                                }
                        )
                    }
                }
            )
        }
    }
}