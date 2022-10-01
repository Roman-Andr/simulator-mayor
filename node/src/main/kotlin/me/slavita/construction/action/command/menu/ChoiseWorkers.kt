package me.slavita.construction.action.command.menu

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.ItemIcons
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player
import java.util.stream.Collectors
import java.util.stream.Stream

class ChoiseWorkers(player: Player, val project: Project) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Selection(
                title = "Выбор строителей",
                rows = 5,
                columns = 5,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    workers.filter {
                            worker -> activeProjects.stream().anyMatch { !(it.structure as WorkerStructure).workers.contains(worker) }
                    }.filter { !(project.structure as WorkerStructure).workers.contains(it) }
                        .forEach { worker ->
                        this@storage.add(
                            ReactiveButton()
                                .item(ItemIcons.get(worker.rarity.iconKey, worker.rarity.iconValue, worker.rarity.iconMaterial))
                                .title(worker.name)
                                .hover(Stream.of(
                                    "${AQUA}Имя: ${worker.name}\n",
                                    "${AQUA}Редкость: ${worker.rarity.title}\n",
                                    "${AQUA}Уровень: ${worker.skill}${WHITE}${Emoji.UP}\n",
                                    "${AQUA}Надёжность: ${worker.reliability}\n",
                                    "${AQUA}Жадность: ${worker.rapacity.title}\n"
                                ).collect(Collectors.joining()))
                                .hint("Выбрать")
                                .onClick { _, _, _ ->
                                    if ((project.structure as WorkerStructure).workers.size < 3) {
                                        project.structure.workers.add(worker)
                                        ChoiseWorkers(player, project).tryExecute()
                                    } else {
                                        Anime.close(player)
                                        this@user.activeProjects.add(project.apply { start() })
                                    }
                                }
                        )
                    }
                }
            )
        }
    }
}