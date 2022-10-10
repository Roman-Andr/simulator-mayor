package me.slavita.construction.action.command.menu.project

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.worker.Worker
import me.slavita.construction.worker.WorkerState
import org.bukkit.entity.Player

class WorkersBuildingMenu(player: Player, val structure: WorkerStructure) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Selection(title = "Настройка рабочих", rows = 3, columns = 3,
                storage = mutableListOf<ReactiveButton>().apply {
                    workers.sortedBy { it.rarity }.sortedBy { structure.workers.contains(it) }.forEach { worker ->
                        add(button {
                            title = worker.name
                            hint = getWorkerState(worker).title
                            special = structure.workers.contains(worker)
                            item = ItemIcons.get("skyblock", "spawn")
                            onClick { _, _, button ->
                                when(getWorkerState(worker)) {
                                    WorkerState.FREE -> {
                                        structure.workers.add(worker)
                                        button.special = true
                                        button.hint = getWorkerState(worker).title
                                    }
                                    WorkerState.SELECTED -> {
                                        structure.workers.remove(worker)
                                        button.special = false
                                        button.hint = getWorkerState(worker).title
                                    }
                                    WorkerState.BUSY -> {
                                        activeProjects.find { if (it.structure is WorkerStructure) it.structure.workers.contains(worker) else false }!!.apply target@ {
                                            (this@target.structure as WorkerStructure).workers.remove(worker)
                                            this@WorkersBuildingMenu.structure.workers.add(worker)
                                            button.special = true
                                            button.hint = getWorkerState(worker).title
                                        }
                                    }
                                }
                            }
                        })
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
        val selectedWorkers = structure.workers

        if (selectedWorkers.contains(targetWorker)) return WorkerState.SELECTED
        if (busyWorkers.contains(targetWorker)) return WorkerState.BUSY
        return WorkerState.FREE
    }
}