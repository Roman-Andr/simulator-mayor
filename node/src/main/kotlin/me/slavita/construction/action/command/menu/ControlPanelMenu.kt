package me.slavita.construction.action.command.menu

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.project.ActiveProjectsMenu
import me.slavita.construction.action.command.menu.project.ProjectsChoice
import me.slavita.construction.action.command.menu.worker.WorkerBuyMenu
import me.slavita.construction.action.command.menu.worker.WorkerTeamMenu
import me.slavita.construction.app
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import org.bukkit.entity.Player

class ControlPanelMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return get(MenuInfo("Меню", StatsType.LEVEL, 4, 3)).apply {
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
                            WorkerBuyMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "Проекты"
                        description = "Получение проектов\nдля строительства"
                        hint = "Выбрать"
                        item = ItemIcons.get("skyblock", "info")
                        onClick { _, _, _ ->
                            ProjectsChoice(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "Активные проекты"
                        description = "Просмостр ваших\nактивных проектов"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "book")
                        onClick { _, _, _ ->
                            ActiveProjectsMenu(player).closeAll(false).tryExecute()
                        }
                    }
                )
            }
        }
    }
}