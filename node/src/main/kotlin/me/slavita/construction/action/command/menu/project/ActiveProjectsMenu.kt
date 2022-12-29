package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.getProjectsInfo
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class ActiveProjectsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run {
            return getBaseSelection(MenuInfo("${AQUA}${BOLD}Ваши активные проекты", StatsType.MONEY, 4, 5)).apply {
                hint = ""
                info = getProjectsInfo()
                storage = cities.flatMap { it.projects }.mapM {
                    button {
                        item = ItemIcons.get("skyblock", "settings")
                        title = "Проект #${it.id}"
                        hover = it.toString()
                        hint = " "
                    }
                }
            }
        }
    }
}