package me.slavita.construction.action.command.menu.achievements

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.MenuCommand
import org.bukkit.entity.Player

class AchievementsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return selection {

        }
    }
}