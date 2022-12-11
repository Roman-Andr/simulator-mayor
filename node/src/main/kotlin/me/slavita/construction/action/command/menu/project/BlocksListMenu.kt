package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.utils.user
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class BlocksListMenu(player: Player, val structure: Structure) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Selection(title = "${AQUA}${BOLD}Список материалов", rows = 5, columns = 14,
                storage = hashSetOf<ItemProperties>().apply {
                    structure.box.forEachBukkit { this.add(ItemProperties.fromBlock(it)) }
                }.map { itemProps ->
                    button {
                        item = itemProps.createItemStack(1)
                        hover = itemProps.createItemStack(1).i18NDisplayName
                        hint = " "
                    }
                }.toMutableList()
            )
        }
    }
}