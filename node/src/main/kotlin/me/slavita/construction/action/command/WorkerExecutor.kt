package me.slavita.construction.action.command

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.worker.Worker
import me.slavita.construction.worker.WorkerState
import org.bukkit.entity.Player

abstract class WorkerExecutor(player: Player, val structure: WorkerStructure) : MenuCommand(player) {
    abstract override fun getMenu(): Openable

    protected fun distributeWorker(targetWorker: Worker, button: ReactiveButton) {
        user.run {
            if (structure.workers.size == 6) return
            when (getWorkerState(targetWorker)) {
                WorkerState.FREE -> {
                    structure.workers.add(targetWorker)
                    button.backgroundColor = GlowColor.ORANGE
                    button.hint = getWorkerState(targetWorker).title
                }

                WorkerState.SELECTED -> {
                    structure.workers.remove(targetWorker)
                    button.backgroundColor = GlowColor.BLUE
                    button.hint = getWorkerState(targetWorker).title
                }

                WorkerState.BUSY -> {}
            }
        }
    }

    protected fun getWorkerState(targetWorker: Worker): WorkerState {
        val busyWorkers = user.data.workers.filter { worker ->
            user.currentCity.projects.stream().anyMatch {
                when (it.structure is WorkerStructure) {
                    true -> (it.structure as WorkerStructure).workers.contains(worker)
                    else -> {
                        false
                    }
                }
            }
        }
        val selectedWorkers = structure.workers

        if (selectedWorkers.contains(targetWorker)) return WorkerState.SELECTED
        if (busyWorkers.contains(targetWorker)) return WorkerState.BUSY
        return WorkerState.FREE
    }
}
