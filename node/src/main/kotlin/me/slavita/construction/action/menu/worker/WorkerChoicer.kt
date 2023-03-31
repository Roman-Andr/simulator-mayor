package me.slavita.construction.action.menu.worker

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.region.StructureOptions
import me.slavita.construction.region.WorkerStructure
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.MAX_BUILDING_WORKERS
import me.slavita.construction.utils.WORKER_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.getEmptyButton
import me.slavita.construction.utils.size
import me.slavita.construction.worker.Worker
import me.slavita.construction.worker.WorkerState
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class WorkerChoicer(
    player: Player,
    val options: StructureOptions,
    val selectedWorkers: HashSet<Worker>,
    val structure: WorkerStructure? = null,
    val onSubmit: (selectedWorkers: HashSet<Worker>) -> Unit
) : MenuCommand(player) {

    private val workersCountPattern = "${AQUA}Работников: $GOLD%value%$GRAY/$GOLD%max%"

    private var submitButton = button {
        item = Icons.get("other", "access")
        title = "${GREEN}Подтвердить"
        hint = "Готово"
        hover = workersCountPattern
            .replace("%value%", "0")
            .replace("%max%", "$MAX_BUILDING_WORKERS")
        click { _, _, _ ->
            if (selectedWorkers.isNotEmpty()) {
                Anime.close(user.player)
                onSubmit(selectedWorkers)
            }
        }
    }

    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${AQUA}${BOLD}Выбор строителей"
                info = WORKER_INFO
                size(5, 4)
                updateSubmit()
                storage = mutableListOf(
                    getEmptyButton(),
                    submitButton,
                    button {
                        item = Icons.get("other", "reload")
                        title = """
                        ${GREEN}Убрать
                        ${GREEN}выделение
                        """.trimIndent()
                        backgroundColor = GlowColor.RED
                        hint = "Убрать"
                        click { _, _, _ ->
                            selectedWorkers.clear()
                        }
                    },
                    getEmptyButton()
                ).apply storage@{
                    this@user.data.workers.sortedByDescending { it.rarity }.forEach { worker ->
                        this@storage.add(
                            button {
                                item = Icons.get(
                                    worker.rarity.iconKey,
                                    worker.rarity.iconValue,
                                    false,
                                    worker.rarity.iconMaterial
                                )
                                title = worker.name
                                hover = worker.toString()
                                hint = getWorkerState(worker).title
                                backgroundColor = getWorkerState(worker).color
                                click { _, _, button ->
                                    clickWorker(worker, button)
                                    updateSubmit()
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    fun getWorkerState(worker: Worker): WorkerState {
        if (selectedWorkers.contains(worker)) return WorkerState.SELECTED
        if (worker.parent != structure && worker.parent != null) return WorkerState.BUSY
        return WorkerState.FREE
    }

    fun clickWorker(worker: Worker, button: ReactiveButton) {
        user.run {
            if (selectedWorkers.size >= MAX_BUILDING_WORKERS) return
            when (getWorkerState(worker)) {
                WorkerState.FREE -> {
                    selectedWorkers.add(worker)
                }

                WorkerState.SELECTED -> {
                    selectedWorkers.remove(worker)
                }

                WorkerState.BUSY -> {}
            }

            getWorkerState(worker).run {
                button.backgroundColor = color
                button.hint = title
            }
        }
    }

    fun updateSubmit() {
        selectedWorkers.run {
            submitButton.backgroundColor = when {
                isEmpty() -> GlowColor.NEUTRAL
                size >= MAX_BUILDING_WORKERS -> GlowColor.ORANGE
                else -> GlowColor.GREEN
            }

            submitButton.hover = workersCountPattern
                .replace("%value%", "$size")
                .replace("%max%", "$MAX_BUILDING_WORKERS")
        }
    }
}
