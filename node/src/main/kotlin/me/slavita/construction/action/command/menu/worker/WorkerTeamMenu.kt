package me.slavita.construction.action.command.menu.worker

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import org.bukkit.ChatColor.AQUA
import org.bukkit.entity.Player

class WorkerTeamMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return get(MenuInfo("Ваши строители", StatsType.MONEY, 4, 4)).apply {
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    workers.forEach { worker ->
                        this@storage.add(
                            ReactiveButton()
                                .item(
                                    ItemIcons.get(
                                        worker.rarity.iconKey,
                                        worker.rarity.iconValue,
                                        worker.rarity.iconMaterial
                                    )
                                )
                                .title(worker.name)
                                .hint("${AQUA}Управление")
                                .onClick { _, _, _ ->
                                    WorkerUpgradeMenu(player, worker).closeAll(false).tryExecute()
                                }
                        )
                    }
                }
            }
        }
    }
}