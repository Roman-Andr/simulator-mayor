package me.slavita.construction.action.command.menu.worker

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.WorkerExecutor
import me.slavita.construction.app
import me.slavita.construction.project.ProjectGenerator
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.worker.WorkerState
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class WorkerChoice(player: Player, structure: WorkerStructure) : WorkerExecutor(player, structure) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Selection(title = "Выбор строителей", rows = 5, columns = 4, storage = mutableListOf(
                    getEmptyButton(),
                    button {
                        item = ItemIcons.get("other", "access")
                        title = "${GREEN}Подтвердить"
                        hint = "Готово"
                        onClick { _, _, _ ->
                            Anime.close(player)
                            this@user.activeProjects.add(ProjectGenerator.generateWorker(this@user).apply {
                                (this.structure as WorkerStructure).workers.addAll(this@WorkerChoice.structure.workers)
                                start()
                            })
                        }},
                    button {
                        item = ItemIcons.get("other", "reload")
                        title = "${GREEN}Убрать\nвыделение"
                        hint = "Убрать"
                        onClick { _, _, _ ->
                            structure.workers.clear()
                            WorkerChoice(player, structure).tryExecute()
                        }},
                    getEmptyButton()
                ).apply storage@{
                    workers.forEach { worker ->
                        this@storage.add(
                            button {
                                item = ItemIcons.get(worker.rarity.iconKey, worker.rarity.iconValue, worker.rarity.iconMaterial)
                                title = worker.name
                                hover = worker.toString()
                                hint = getWorkerState(worker).title
                                special = getWorkerState(worker) == WorkerState.SELECTED
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