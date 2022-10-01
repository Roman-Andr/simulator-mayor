package me.slavita.construction.action.command.menu

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.ui.ItemIcons
import org.bukkit.entity.Player

class BuildingInfoMenu(player: Player, val project: Project) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Selection(
                title = "Процесс постройки",
                rows = 3,
                columns = 3,
                storage = mutableListOf(
                    ReactiveButton()
                        .title("Строители")
                        .description("Просмотреть выбранных\nстроителей")
                        .hint("Выбрать")
                        .item(ItemIcons.get("other", "myfriends"))
                        .onClick { _, _, _ ->

                        },
                    ReactiveButton()
                        .title("Информация")
                        .hint("")
                        .hover("")
                        .item(ItemIcons.get("skyblock", "spawn"))
                )
            )
        }
    }
}