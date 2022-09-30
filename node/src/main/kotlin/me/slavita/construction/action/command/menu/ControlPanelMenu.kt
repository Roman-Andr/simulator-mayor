package me.slavita.construction.action.command.menu

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import org.bukkit.entity.Player

class ControlPanelMenu(player: Player) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = "Меню",
                vault = Emoji.EXP,
                money = "Ваш уровень ${stats.level}",
                rows = 4,
                columns = 3,
                storage = mutableListOf(
                    ReactiveButton()
                        .title("Работники")
                        .description("Управление вашими\nработниками")
                        .hint("Выбрать")
                        .item(ItemIcons.get("skyblock", "crafts"))
                        .onClick { _, _, _ ->
                            WorkerTeamMenu(player).closeAll(false).tryExecute()
                        },
                    ReactiveButton()
                        .title("Покупка работников")
                        .description("Покупка рабочих")
                        .hint("Выбрать")
                        .item(ItemIcons.get("skyblock", "crafts"))
                        .onClick { _, _, _ ->
                            BuyWorkersMenu(player).closeAll(false).tryExecute()
                        },
                    ReactiveButton()
                        .title("Проекты")
                        .description("Получение проектов\nдля строительства")
                        .hint("Выбрать")
                        .item(ItemIcons.get("skyblock", "crafts"))
                        .onClick { _, _, _ ->
                            ProjectsChoise(player).closeAll(false).tryExecute()
                        },
                    ReactiveButton()
                        .title("Активные проекты")
                        .description("Просмостр ваших\nактивных проектов")
                        .hint("Выбрать")
                        .item(ItemIcons.get("skyblock", "crafts"))
                        .onClick { _, _, _ ->
                            ActiveProjectsMenu(player).closeAll(false).tryExecute()
                        },
                )
            )
        }
    }
}