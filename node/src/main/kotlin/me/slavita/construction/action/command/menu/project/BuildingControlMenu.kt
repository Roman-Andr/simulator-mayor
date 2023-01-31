package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.worker.WorkerChoice
import me.slavita.construction.city.project.Project
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.click
import me.slavita.construction.utils.size
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class BuildingControlMenu(player: Player, val project: Project) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${AQUA}${BOLD}Процесс постройки"
                size(3, 3)
                storage = mutableListOf(
                    button {
                        title = "Список материалов"
                        description = "Просмотреть список\nнеобходимых материалов"
                        hint = "Выбрать"
                        item = Icons.get("skyblock", "info")
                        click { _, _, _ ->
                            BlocksListMenu(player, project.structure.structure).keepHistory().tryExecute()
                        }
                    }).apply {
                    if (project.structure !is WorkerStructure) return@apply
                    add(button {
                        title = "Строители"
                        description = "Просмотреть выбранных\nстроителей"
                        hint = "Выбрать"
                        item = Icons.get("other", "myfriends")
                        click { _, _, _ ->
                            WorkerChoice(player, project, false).tryExecute()
                        }
                    })
                }
            }
        }
    }
}

