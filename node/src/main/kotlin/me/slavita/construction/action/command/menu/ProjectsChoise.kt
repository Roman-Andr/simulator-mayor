package me.slavita.construction.action.command.menu

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.choicer.Choicer
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.project.ProjectStatistics
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.ItemIcons
import org.bukkit.entity.Player

class ProjectsChoise(player: Player) : OpenCommand(player) {
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
                        .item(ItemIcons.get("skyblock", "crafts"))
                        .onClick { _, _, _ ->
                            Anime.close(player)
                        },
                    ReactiveButton()
                        .title("Рабочие")
                        .description("Проект будут \nстроить выбранные\nвами строители")
                        .hint("Выбрать")
                        .item(ItemIcons.get("skyblock", "crafts"))
                        .onClick { _, _, _ ->
                            ChoiseWorkers(player, Project(
                                this,
                                activeProjects.size,
                                WorkerStructure(
                                    app.mainWorld,
                                    Structures.structureGroups.random().structures.random(),
                                    player,
                                    app.mainWorld.map.getLabels("default", "1")[0],
                                    mutableSetOf()
                                ),
                                ProjectStatistics(1000)
                            )
                            ).tryExecute()
                        }
                )
            )
        }
    }
}