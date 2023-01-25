package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.instance.StructureGroup
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.STRUCTURES_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.mapM
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class ChoiceStructure(
    player: Player,
    val structureGroup: StructureGroup,
    val action: (structure: Structure) -> Unit,
) :
    MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${GOLD}${BOLD}Выбор здания"
                info = STRUCTURES_INFO
                rows = 5
                columns = 4
                storage = structureGroup.structures.sortedBy { it.id }.mapM { structure ->
                    button {
                        title = structure.name
                        hint = "Выбрать"
                        item = Icons.get("skyblock", "spawn")
                        hover = ""
                        structure.blocks.toList().sortedBy { it.second }.toMap().forEach { block ->
                            hover += "${GOLD}${
                                LanguageHelper.getItemDisplayName(
                                    block.key.createItemStack(1),
                                    player
                                )
                            } - ${GREEN}${block.value}шт\n"
                        }
                        click { _, _, _ ->
                            action(structure)
                        }
                    }
                }
            }
        }
    }
}
