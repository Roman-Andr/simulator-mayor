package me.slavita.construction.action

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.utils.user
import me.slavita.construction.worker.Worker
import me.slavita.construction.worker.WorkerState
import org.bukkit.entity.Player

abstract class WorkerExecutor(player: Player, val structure: WorkerStructure) : MenuCommand(player) {
    abstract override fun getMenu(): Openable

    protected fun distributeWorker(targetWorker: Worker, button: ReactiveButton) {
        player.user.run {
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

                WorkerState.BUSY -> {
                    currentCity.projects.find {
                        if (it.structure is WorkerStructure) it.structure.workers.contains(
                            targetWorker
                        ) else false
                    }!!
                        .apply target@{
                            (this@target.structure as WorkerStructure).workers.remove(targetWorker)
                            structure.workers.add(targetWorker)
                            button.backgroundColor = GlowColor.ORANGE
                            button.hint = getWorkerState(targetWorker).title
                        }
                }
            }
        }
    }

    protected fun getWorkerState(targetWorker: Worker): WorkerState {
        val busyWorkers = player.user.stats.workers.filter { worker ->
            player.user.currentCity.projects.stream().anyMatch {
                when (it.structure is WorkerStructure) {
                    true -> it.structure.workers.contains(worker)
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