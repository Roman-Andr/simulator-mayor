package me.slavita.construction.action.command.menu.achievements

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import org.bukkit.entity.Player

class AchievementsChoiceMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {

        }
    }
}