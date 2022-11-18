package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.worker.WorkerChoice
import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.entity.Player

class BuildingControlMenu(player: Player, val project: Project) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@{
            return Selection(title = "Процесс постройки", rows = 3, columns = 3,
                storage = mutableListOf(
                    button {
                        title = "Информация"
                        hint = "Выбрать"
                        item = ItemIcons.get("skyblock", "spawn")
                        onClick { _, _, _ ->
                            BuildingInfoMenu(player, project).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "Список материалов"
                        description = "Просмотреть список\nнеобходимых материалов"
                        hint = "Выбрать"
                        item = ItemIcons.get("skyblock", "info")
                        onClick { _, _, _ ->
                            BlocksListMenu(player, project.structure.structure).closeAll(false).tryExecute()
                        }
                    }).apply {
                    if (project.structure !is WorkerStructure) return@apply
                    add(button {
                        title = "Строители"
                        description = "Просмотреть выбранных\nстроителей"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "myfriends")
                        onClick { _, _, _ ->
                            WorkerChoice(player, project, false).tryExecute()
                        }
                    })
                }
            )
        }
    }
}