package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.project.Project
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class BuildingInfoMenu(player: Player, val project: Project) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Selection(title = "${GOLD}${BOLD}Информация об постройке", rows = 3, columns = 3,
                storage = mutableListOf(
                    button {
                        title = "Время"
                        hint = "Выбрать"
                        item = ItemIcons.get("skyblock", "spawn")
                        hover = "Время до конца постройки: ${project.timeLast}"
                    },
                    button {
                        title = "Материалы"
                        description = "Просмотреть список\nнеобходимых материалов"
                        hint = "Выбрать"
                        item = ItemIcons.get("skyblock", "info")
                        onClick { _, _, _ ->
                            BlocksListMenu(player, project.structure.structure).closeAll(false).tryExecute()
                        }
                    }).apply {
                    if (project.structure !is WorkerStructure) return@apply
                    add(button {
                        title = "Скорость"
                        description = "Просмотреть выбранных\nстроителей"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "myfriends")
                        hover = "Скорость постройки: ${project.structure.workers.sumOf { it.blocksSpeed }} в секунду"
                    })
                }
            )
        }
    }
}