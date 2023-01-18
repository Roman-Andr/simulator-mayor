package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.getStructuresInfo
import me.slavita.construction.utils.mapM
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class ChoiceStructureGroup(
    player: Player,
    val cell: PlayerCell,
    val action: (group: Structure) -> Unit,
) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${GOLD}${BOLD}Выбор здания"
                info = getStructuresInfo()
                rows = 5
                columns = 4
                storage = Structures.structureGroups.mapM { group ->
                    button {
                        title = group.name
                        hint = "Выбрать"
                        item = Icons.get("skyblock", "spawn")
                        onClick { _, _, _ ->
                            ChoiceStructure(player, group, cell) { structure ->
                                action(structure)
                            }.tryExecute()
                        }
                    }
                }
            }
        }
    }
}
