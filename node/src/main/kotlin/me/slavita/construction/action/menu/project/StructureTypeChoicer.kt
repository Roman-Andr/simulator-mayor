package me.slavita.construction.action.menu.project

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.action.menu.worker.WorkerChoicer
import me.slavita.construction.region.*
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.click
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class StructureTypeChoicer(player: Player, val options: StructureOptions, val cell: ClassicCell) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${ChatColor.GOLD}${ChatColor.BOLD}Выбор проекта"
                description = "Выберите тип проекта"
                info = "В данном меню\nвам необходимо выбрать,\nкакой тип проекта\nвы хотите взять для постройки."
                storage = mutableListOf(
                    button {
                        title = "Лично"
                        description = "Строите вручную"
                        hint = "Выбрать"
                        item = Icons.get("other", "human")
                        click { _, _, _ ->
                            if (cell.child != null) return@click

                            Anime.close(player)
                            cell.changeChild(ClientStructure(options, cell))
                        }
                    },
                    button {
                        title = "Рабочие"
                        description = "Проект строят\nстроители"
                        hint = "Выбрать"
                        item = Icons.get("other", "myfriends")
                        backgroundColor = if (user.data.workers.isEmpty()) GlowColor.NEUTRAL else GlowColor.BLUE
                        click { _, _, _ ->
                            if (user.data.workers.isEmpty() || cell.child != null) return@click

                            WorkerChoicer(player, options, hashSetOf(), null) { selectedWorkers ->
                                cell.changeChild(
                                    WorkerStructure(options, cell).apply {
                                        workers = selectedWorkers
                                    }
                                )
                            }.keepHistory().tryExecute()
                        }
                    }
                )
            }
        }
    }
}
