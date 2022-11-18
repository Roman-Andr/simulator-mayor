package me.slavita.construction.action.command.menu.lootbbox

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import org.bukkit.entity.Player

class UserLootboxesMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@{
            return Selection()
        }
    }
}