package me.slavita.construction.action.command.menu.project

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.worker.WorkerChoice
import me.slavita.construction.app
import me.slavita.construction.project.ProjectGenerator
import me.slavita.construction.structure.Cell
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.Location
import org.bukkit.entity.Player

class ChoiceProjectType(player: Player, val structure: Structure, val cell: Cell) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Choicer(
                title = "Выбор проекта",
                description = "Выберите тип проекта",
                info = "В данном меню\nвам необходимо выбрать,\nкакой тип проекта\nвы хотите взять для постройки.",
                storage = mutableListOf(
                    button {
                        title = "Лично"
                        description = "Вы сами будете\nстроить проект"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "human")
                        onClick { _, _, _ ->
                            val project = ProjectGenerator.generateClient(this@user, structure, cell.apply { busy = true })
                            project.start()
                            city.addProject(project)

                            Anime.close(player)
                        }
                    },
                    button {
                        title = "Рабочие"
                        description = "Проект будут \nстроить выбранные\nвами строители"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "myfriends")
                        onClick { _, _, _ ->
                            WorkerChoice(
                                player,
                                ProjectGenerator.generateWorker(this@user, structure, cell.apply { busy = true })
                            ).tryExecute()
                        }
                    }
                )
            )
        }
    }
}