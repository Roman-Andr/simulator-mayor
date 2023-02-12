package me.slavita.construction.action.menu.worker

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.WorkerExecutor
import me.slavita.construction.action.menu.project.StartProject
import me.slavita.construction.city.project.Project
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.MAX_BUILDING_WORKERS
import me.slavita.construction.utils.WORKER_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.getEmptyButton
import me.slavita.construction.utils.size
import me.slavita.construction.worker.WorkerState
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class WorkerChoice(
    player: Player,
    val project: Project,
    val startProject: Boolean = true,
) : WorkerExecutor(player, project.structure as WorkerStructure) {

    private val pattern = "${AQUA}Работников: $GOLD%value%$GRAY/$GOLD%max%"

    private var submitButton = button {
        item = Icons.get("other", "access")
        title = "${GREEN}Подтвердить"
        hint = "Готово"
        hover = pattern
            .replace("%value%", "0")
            .replace("%max%", "$MAX_BUILDING_WORKERS")
        click { _, _, _ ->
            if ((project.structure as WorkerStructure).workers.isNotEmpty()) {
                Anime.close(user.player)
                if (!startProject) return@click
                StartProject(user, project, structure).tryExecute()
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
                            if (structure.workers.isEmpty()) return@click
                            structure.workers.clear()
                            WorkerChoice(player, project).tryExecute()
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
                                backgroundColor =
                                    when (getWorkerState(worker)) {
                                        WorkerState.SELECTED -> GlowColor.ORANGE
                                        WorkerState.BUSY -> GlowColor.NEUTRAL
                                        WorkerState.FREE -> GlowColor.BLUE
                                    }
                                click { _, _, button ->
                                    distributeWorker(worker, button)
                                    updateSubmit()
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun updateSubmit() {
        (project.structure as WorkerStructure).workers.run {
            submitButton.backgroundColor = when {
                isEmpty() -> GlowColor.NEUTRAL
                size == 6 -> GlowColor.ORANGE
                else -> GlowColor.GREEN
            }
            submitButton.hover = pattern
                .replace("%value%", "$size")
                .replace("%max%", "$MAX_BUILDING_WORKERS")
        }
    }
}
