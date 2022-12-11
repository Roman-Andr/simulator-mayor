package me.slavita.construction.action.command.menu

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.choicer.Choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class SettingsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run {
            return Choicer(title = "${GREEN}${BOLD}Настройки", description = "Настройка игровых аспектов").apply {
                storage = mutableListOf(

                )
            }
        }
    }
}