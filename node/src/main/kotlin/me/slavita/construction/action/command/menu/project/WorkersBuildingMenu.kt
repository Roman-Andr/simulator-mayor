package me.slavita.construction.action.command.menu.project

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.WorkerExecutor
import me.slavita.construction.app
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.entity.Player

class WorkersBuildingMenu(player: Player, structure: WorkerStructure) : WorkerExecutor(player, structure) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Selection(title = "Настройка рабочих", rows = 3, columns = 3,
                storage = mutableListOf<ReactiveButton>().apply {
                    workers.sortedBy { it.rarity }.sortedBy { structure.workers.contains(it) }.forEach { worker ->
                        add(button {
                            title = worker.name
                            hint = getWorkerState(worker).title
                            backgroundColor =  if (structure.workers.contains(worker)) GlowColor.ORANGE else GlowColor.BLUE
                            item = ItemIcons.get("skyblock", "spawn")
                            onClick { _, _, button ->
                                distributeWorker(worker, button)
                            }
                        })
                    }
                }
            )
        }
    }
}