package me.slavita.construction.action.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.STRUCTURES_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.size
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class ChoiceStructureGroup(
    player: Player,
    val action: (group: Structure) -> Unit,
) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${GOLD}${BOLD}Выбор здания"
                info = STRUCTURES_INFO
                size(5, 4)
                storage = Structures.structureGroups.mapM { group ->
                    button {
                        title = group.name
                        hint = "Выбрать"
                        item = Icons.get("skyblock", "spawn")
                        click { _, _, _ ->
                            ChoiceStructure(player, group) { structure ->
                                action(structure)
                            }.tryExecute()
                        }
                    }
                }
            }
        }
    }
}
