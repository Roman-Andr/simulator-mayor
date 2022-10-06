package me.slavita.construction.action.command.menu.worker

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.ItemIcons
import org.bukkit.ChatColor.AQUA
import org.bukkit.entity.Player

class WorkerTeamMenu(player: Player) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = "Ваши строители",
                vault = Emoji.DOLLAR,
                money = "Ваш баланс ${stats.money.toMoney()}",
                rows = 4,
                columns = 4,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    workers.forEach { worker ->
                        this@storage.add(
                            ReactiveButton()
                                .item(ItemIcons.get(worker.rarity.iconKey, worker.rarity.iconValue, worker.rarity.iconMaterial))
                                .title(worker.name)
                                .hint("${AQUA}Управление")
                                .onClick { _, _, _ ->
                                    WorkerUpgradeMenu(player, worker).closeAll(false).tryExecute()
                                }
                        )
                    }
                }
            )
        }
    }
}