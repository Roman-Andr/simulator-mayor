package me.slavita.construction.action.command.menu

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.lootbbox.BuyLootboxMenu
import me.slavita.construction.action.command.menu.project.ActiveProjectsMenu
import me.slavita.construction.action.command.menu.worker.WorkerTeamMenu
import me.slavita.construction.app
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import org.bukkit.entity.Player

class ControlPanelMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Choicer(title = "Меню", description = "Выбери нужный раздел").apply {
                storage = mutableListOf(
                    button {
                        title = "Работники"
                        description = "Управление вашими\nработниками"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "guild_members")
                        onClick { _, _, _ ->
                            WorkerTeamMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "Покупка работников"
                        description = "Покупка рабочих"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "guild_members_add")
                        onClick { _, _, _ ->
                            BuyLootboxMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "Активные проекты"
                        description = "Просмотр ваших\nактивных проектов"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "book")
                        onClick { _, _, _ ->
                            ActiveProjectsMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "Локации"
                        description = "Перемещение\nмежду локациями"
                        hint = "Выбрать"
                        item = ItemIcons.get("alpha", "islands")
                        onClick { _, _, _ ->
                            LocationsMenu(player).closeAll(false).tryExecute()
                        }
                    }
                )
            }
        }
    }
}