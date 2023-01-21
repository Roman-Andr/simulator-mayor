package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.click
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class ChoiceStructureGroup(player: Player, val playerCell: PlayerCell) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return selection {
                title = "${GOLD}${BOLD}Выбор здания"
                rows = 5
                columns = 4
                storage = Structures.structureGroups.mapM { structureGroup ->
                    button {
                        title = structureGroup.name
                        hint = "Выбрать"
                        item = ItemIcons.get("skyblock", "spawn")
                        click { _, _, _ ->
                            ChoiceStructure(player, structureGroup, playerCell).tryExecute()
                        }
                    }
                }
            }
        }
    }
}
