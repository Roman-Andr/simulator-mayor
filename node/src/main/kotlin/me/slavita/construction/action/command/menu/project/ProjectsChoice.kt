package me.slavita.construction.action.command.menu.project

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.choicer.Choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.worker.WorkerChoice
import me.slavita.construction.app
import me.slavita.construction.project.ProjectGenerator
import me.slavita.construction.ui.ItemIcons
import org.bukkit.entity.Player

class ProjectsChoice(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Choicer(
                title = "Выбор проекта",
                description = "Выберите тип проекта",
                info = "В данном меню\nвам необходимо выбрать,\nкакой тип проекта\nвы хотите взять для постройки.",
                storage = mutableListOf(
                    ReactiveButton()
                        .title("Лично")
                        .description("Вы сами будете\nстроить проект")
                        .hint("Выбрать")
                        .item(ItemIcons.get("other", "human"))
                        .onClick { _, _, _ ->
                            Anime.close(player)
                            activeProjects.add(ProjectGenerator.generateClient(this).apply {
                                watchableProject = this
                                start()
                            })
                        },
                    ReactiveButton()
                        .title("Рабочие")
                        .description("Проект будут \nстроить выбранные\nвами строители")
                        .hint("Выбрать")
                        .item(ItemIcons.get("other", "myfriends"))
                        .onClick { _, _, _ ->
                            WorkerChoice(player, ProjectGenerator.generateWorker(this).apply { watchableProject = this }).tryExecute()
                        }
                )
            )
        }
    }
}