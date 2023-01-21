package me.slavita.construction.action.command.menu.project

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.worker.WorkerChoice
import me.slavita.construction.project.ProjectGenerator
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.click
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class ChoiceProject(player: Player, val structure: Structure, val playerCell: PlayerCell) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return choicer {
                title = "${GOLD}${BOLD}Выбор проекта"
                description = "Выберите тип проекта"
                info = "В данном меню\nвам необходимо выбрать,\nкакой тип проекта\nвы хотите взять для постройки."
                storage = mutableListOf(
                    button {
                        title = "Лично"
                        description = "Строите вручную"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "human")
                        click { _, _, _ ->
                            playerCell.setBusy()
                            val project = ProjectGenerator.generateClient(this@user, structure, playerCell)

                            project.start()
                            currentCity.addProject(project)

                            Anime.close(player)
                        }
                    },
                    button {
                        title = "Рабочие"
                        description = "Проект строят\nстроители"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "myfriends")
                        click { _, _, _ ->
                            WorkerChoice(
                                player,
                                ProjectGenerator.generateWorker(this@user, structure, playerCell)
                            ).tryExecute()
                        }
                    }
                )
            }
        }
    }
}
