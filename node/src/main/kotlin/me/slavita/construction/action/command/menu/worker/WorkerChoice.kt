package me.slavita.construction.action.command.menu.worker

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.worker.Worker
import me.slavita.construction.worker.WorkerState
import org.bukkit.ChatColor.GREEN
import org.bukkit.Material
import org.bukkit.entity.Player

class WorkerChoice(player: Player, val project: Project) : OpenCommand(player) {
    val user = app.getUser(player)

    override fun getMenu(): Openable {
        if (project.structure !is WorkerStructure) return Selection()
        user.run user@ {
            return Selection(
                title = "Выбор строителей",
                rows = 5,
                columns = 4,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    this@storage.add(
                        ReactiveButton()
                            .material(Material.AIR)
                            .hint(""))
                    this@storage.add(
                        ReactiveButton()
                            .item(ItemIcons.get("other", "access"))
                            .title("${GREEN}Подтвердить")
                            .hint("Готово")
                            .onClick { _, _, _ ->
                                Anime.close(player)
                                this@user.activeProjects.add(project.apply { start() })
                            })
                    this@storage.add(
                        ReactiveButton()
                            .item(ItemIcons.get("other", "reload"))
                            .title("${GREEN}Убрать\nвыделение")
                            .hint("Убрать")
                            .onClick { _, _, _ ->
                                project.structure.workers.clear()
                                WorkerChoice(player, project).tryExecute()
                            })
                    this@storage.add(
                        ReactiveButton()
                            .material(Material.AIR)
                            .hint(""))
                    workers.forEach { worker ->
                        this@storage.add(
                            ReactiveButton()
                                .item(ItemIcons.get(worker.rarity.iconKey, worker.rarity.iconValue, worker.rarity.iconMaterial))
                                .title(worker.name)
                                .hover(worker.toString())
                                .hint(when(getWorkerState(worker)) {
                                        WorkerState.FREE -> "Выбрать"
                                        WorkerState.SELECTED -> "Выбран"
                                        WorkerState.BUSY -> "Занят"
                                    })
                                .special(getWorkerState(worker) == WorkerState.SELECTED)
                                .onClick { _, _, button ->
                                    when(getWorkerState(worker)) {
                                        WorkerState.FREE -> {
                                            project.structure.workers.add(worker)
                                            button.special = true
                                        }
                                        WorkerState.SELECTED -> {
                                            project.structure.workers.remove(worker)
                                            button.special = false
                                        }
                                        WorkerState.BUSY -> {
                                            activeProjects.find { if (it.structure is WorkerStructure) it.structure.workers.contains(worker) else false }!!.apply {
                                                (structure as WorkerStructure).workers.remove(worker)
                                                project.structure.workers.add(worker)
                                                button.special = true
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
        val busyWorkers = user.workers.filter { worker -> user.activeProjects.stream().anyMatch { (it.structure as WorkerStructure).workers.contains(worker) }}
        val selectedWorkers = (project.structure as WorkerStructure).workers

        if (selectedWorkers.contains(targetWorker)) return WorkerState.SELECTED
        if (busyWorkers.contains(targetWorker)) return WorkerState.BUSY
        return WorkerState.FREE
    }
}