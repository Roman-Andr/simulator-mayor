package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.instance.StructureGroup
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class ChoiceStructure(player: Player, val structureGroup: StructureGroup, val playerCell: PlayerCell) :
    MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return selection {
                title = "${GOLD}${BOLD}Выбор здания"
                rows = 5
                columns = 4
                storage = structureGroup.structures.mapM { structure ->
                    button {
                        title = structure.name
                        hint = "Выбрать"
                        item = ItemIcons.get("skyblock", "spawn")
                        hover = ""
                        structure.blocks.toList().sortedByDescending { it.second }.toMap().forEach { block ->
                            hover += "${GOLD}${
                                LanguageHelper.getItemDisplayName(
                                    block.key.createItemStack(1),
                                    player
                                )
                            } - ${GREEN}${block.value}шт\n"
                        }
                        onClick { _, _, _ ->
                            ChoiceProject(player, structure, playerCell).tryExecute()
                        }
                    }
                }
            }
        }
    }
}
