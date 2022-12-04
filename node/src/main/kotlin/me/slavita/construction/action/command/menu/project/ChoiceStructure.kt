package me.slavita.construction.action.command.menu.project

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.structure.Cell
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class ChoiceStructure(player: Player, val cell: Cell) : MenuCommand(player) {
    override fun getMenu(): Openable {
        val structures = arrayListOf<ReactiveButton>()
        Structures.structureGroups.forEach { group ->
            group.structures.forEach { structure ->
                structures.add(
                    button {
                        title = structure.name
                        hint = "Выбрать"
                        item = ItemIcons.get("skyblock", "spawn")
                        onClick { _, _, _ ->
                            ChoiceProject(player, structure, cell).tryExecute()
                        }
                    }
                )
            }
        }

        player.user.run user@{
            return Selection(title = "${GOLD}${BOLD}Выбор здания", rows = 5, columns = 4, storage = structures)
        }
    }
}