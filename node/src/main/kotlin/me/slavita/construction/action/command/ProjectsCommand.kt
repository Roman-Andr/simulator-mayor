package me.slavita.construction.action.command

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import org.bukkit.entity.Player

class ProjectsCommand(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = "Ваши строители",
                money = "Ваш баланс ${stats.money}",
                rows = 4,
                columns = 5,
                storage = mutableListOf(
                    ReactiveButton()
                        .title("Получить проект")
                        .description("Вы сами будете его строить")
                        .item(ItemIcons.get("skyblock", "crafts"))
                        .onClick { _, _, _ ->
                            Anime.close(player)
                            app.mainWorld.playerBuild(player)
                        },
                    ReactiveButton()
                        .title("Получить проект")
                        .description("За вас построят все ваши строители")
                        .item(ItemIcons.get("skyblock", "crafts"))
                        .onClick { _, _, _ ->
                            Anime.close(player)
                            app.mainWorld.playerWorkerBuild(player)
                        }
                )
            )
        }
    }
}