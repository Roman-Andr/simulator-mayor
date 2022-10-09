package me.slavita.construction.action.command.menu.project

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.entity.Player

class BuildingInfoMenu(player: Player, val project: Project) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Selection(title = "Процесс постройки", rows = 3, columns = 3,
                storage = mutableListOf(
                    ReactiveButton()
                        .title("Информация")
                        .hint("")
                        .hover("")
                        .item(ItemIcons.get("skyblock", "spawn")),
                    ReactiveButton()
                        .title("Строители")
                        .description("Просмотреть выбранных\nстроителей")
                        .hint("Выбрать")
                        .item(ItemIcons.get("other", "myfriends"))
                        .onClick { _, _, _ ->

                        },
                    ReactiveButton()
                        .title("Список материалов")
                        .description("Просмотреть список\nнеобходимых материалов")
                        .hint("Выбрать")
                        .item(ItemIcons.get("skyblock", "info"))
                        .onClick { _, _, _ ->
                            BlocksListMenu(player, project.structure.structure).closeAll(false).tryExecute()
                        }
                )
            )
        }
    }
}