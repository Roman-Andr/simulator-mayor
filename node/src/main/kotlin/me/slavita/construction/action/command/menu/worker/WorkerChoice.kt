package me.slavita.construction.action.command.menu.worker

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.WorkerExecutor
import me.slavita.construction.project.Project
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.getEmptyButton
import me.slavita.construction.utils.user
import me.slavita.construction.worker.WorkerState
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class WorkerChoice(player: Player, val project: Project, val startProject: Boolean = true) :
    WorkerExecutor(player, project.structure as WorkerStructure) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Selection(title = "${AQUA}${BOLD}Выбор строителей", rows = 5, columns = 4, storage = mutableListOf(
                getEmptyButton(),
                button {
                    item = ItemIcons.get("other", "access")
                    title = "${GREEN}Подтвердить"
                    hint = "Готово"
                    onClick { _, _, _ ->
                        Anime.close(player)

                        if (!startProject) return@onClick
                        project.structure.cell.setBusy()
                        (project.structure as WorkerStructure).workers.addAll(this@WorkerChoice.structure.workers)
                        project.start()
                        this@user.currentCity.addProject(project)
                    }
                },
                button {
                    item = ItemIcons.get("other", "reload")
                    title = "${GREEN}Убрать\nвыделение"
                    hint = "Убрать"
                    onClick { _, _, _ ->
                        structure.workers.clear()
                        WorkerChoice(player, project).tryExecute()
                    }
                },
                getEmptyButton()
            ).apply storage@{
                data.workers.sortedByDescending { it.rarity }.forEach { worker ->
                    this@storage.add(
                        button {
                            item = ItemIcons.get(
                                worker.rarity.iconKey,
                                worker.rarity.iconValue,
                                false,
                                worker.rarity.iconMaterial
                            )
                            title = worker.name
                            hover = worker.toString()
                            hint = getWorkerState(worker).title
                            backgroundColor =
                                when (getWorkerState(worker)) {
                                    WorkerState.SELECTED -> GlowColor.ORANGE
                                    WorkerState.BUSY     -> GlowColor.NEUTRAL
                                    WorkerState.FREE     -> GlowColor.BLUE
                                }
                            onClick { _, _, button ->
                                distributeWorker(worker, button)
                            }
                        }
                    )
                }
            }
            )
        }
    }
}