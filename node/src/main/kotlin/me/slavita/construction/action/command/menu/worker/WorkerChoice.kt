package me.slavita.construction.action.command.menu.worker

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.func.mod.util.after
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.worker.Worker
import me.slavita.construction.worker.WorkerState
import org.bukkit.ChatColor.GREEN
import org.bukkit.Material
import org.bukkit.entity.Player

class WorkerChoice(player: Player, val project: Project) : MenuCommand(player) {
    override fun getMenu(): Openable {
        if (project.structure !is WorkerStructure) return Selection()
        app.getUser(player).run user@ {
            return Selection(title = "Выбор строителей", rows = 5, columns = 4,
                storage = mutableListOf(
                    button {
                        material(Material.AIR)
                        hint = ""
                        enabled = false
                    },
                    button {
                        item = ItemIcons.get("other", "access")
                        title = "${GREEN}Подтвердить"
                        hint = "Готово"
                        onClick { _, _, button ->
                            if (project.structure.workers.isEmpty()) {
                                button.hover = "Выберите минимум 1 строителя"
                                return@onClick
                            }
                            Anime.close(player)
                            this@user.activeProjects.add(project.apply {
                                println("added")
                                start()
                            })
                            println(this@user.activeProjects.size)
                            after((2)){ println(this@user.activeProjects.size) }
                        }},
                    button {
                        item = ItemIcons.get("other", "reload")
                        title = "${GREEN}Убрать\nвыделение"
                        hint = "Убрать"
                        onClick { _, _, _ ->
                            project.structure.workers.clear()
                            WorkerChoice(player, project).tryExecute()
                        }},
                    button {
                        material(Material.AIR)
                        hint = ""
                        enabled = false
                    }
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
                                    when(getWorkerState(worker)) {
                                        WorkerState.FREE -> {
                                            project.structure.workers.add(worker)
                                            button.special = true
                                            button.hint = getWorkerState(worker).title
                                        }
                                        WorkerState.SELECTED -> {
                                            project.structure.workers.remove(worker)
                                            button.special = false
                                            button.hint = getWorkerState(worker).title
                                        }
                                        WorkerState.BUSY -> {
                                            activeProjects.find { if (it.structure is WorkerStructure) it.structure.workers.contains(worker) else false }!!.apply {
                                                (structure as WorkerStructure).workers.remove(worker)
                                                project.structure.workers.add(worker)
                                                button.special = true
                                                button.hint = getWorkerState(worker).title
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            )
        }
    }

    private fun getWorkerState(targetWorker: Worker): WorkerState {
        val busyWorkers = app.getUser(player).workers.filter { worker -> app.getUser(player).activeProjects.stream().anyMatch {
            when (it.structure is WorkerStructure) { true -> it.structure.workers.contains(worker)
            else -> { false }
        } }}
        val selectedWorkers = (project.structure as WorkerStructure).workers

        if (selectedWorkers.contains(targetWorker)) return WorkerState.SELECTED
        if (busyWorkers.contains(targetWorker)) return WorkerState.BUSY
        return WorkerState.FREE
    }
}