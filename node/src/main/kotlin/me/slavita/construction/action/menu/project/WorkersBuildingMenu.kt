package me.slavita.construction.action.menu.project

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.WorkerExecutor
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.click
import me.slavita.construction.utils.size
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class WorkersBuildingMenu(player: Player, structure: WorkerStructure) : WorkerExecutor(player, structure) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${AQUA}${BOLD}Настройка рабочих"
                size(3, 3)
                storage = mutableListOf<ReactiveButton>().apply {
                    this@user.data.workers.sortedBy { it.rarity }.sortedBy { structure.workers.contains(it) }
                        .forEach { worker ->
                            add(
                                button {
                                    title = worker.name
                                    hint = getWorkerState(worker).title
                                    backgroundColor =
                                        if (structure.workers.contains(worker)) GlowColor.ORANGE else GlowColor.BLUE
                                    item = Icons.get("skyblock", "spawn")
                                    click { _, _, button ->
                                        distributeWorker(worker, button)
                                    }
                                }
                            )
                        }
                }
            }
        }
    }
}
