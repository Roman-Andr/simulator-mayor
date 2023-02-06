package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.size
import me.slavita.construction.utils.validate
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class BlocksListMenu(player: Player, val structure: Structure) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${AQUA}${BOLD}Список материалов"
                size(5, 14)
                storage = structure.blocks.keys.mapM { itemProps ->
                    button {
                        item = itemProps.createItemStack(1).validate()
                        hover = "${GREEN}${
                            LanguageHelper.getItemDisplayName(
                                itemProps.createItemStack(1),
                                player
                            )
                        } - ${structure.blocks[itemProps]}шт"
                        hint = " "
                    }
                }
            }
        }
    }
}
