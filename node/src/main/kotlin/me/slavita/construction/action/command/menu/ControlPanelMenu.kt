package me.slavita.construction.action.command.menu

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.lootbbox.BuyLootboxMenu
import me.slavita.construction.action.command.menu.project.ActiveProjectsMenu
import me.slavita.construction.action.command.menu.worker.WorkerTeamMenu
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class ControlPanelMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run {
            return Choicer(title = "${GREEN}${BOLD}Меню", description = "Выбери нужный раздел").apply {
                storage = mutableListOf(
                    button {
                        title = "${AQUA}${BOLD}Работники"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE
                        item = ItemIcons.get("other", "guild_members")
                        onClick { _, _, _ ->
                            WorkerTeamMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Локации"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.GREEN
                        item = ItemIcons.get("alpha", "islands")
                        onClick { _, _, _ ->
                            LocationsMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "${AQUA}${BOLD}Активные\n${AQUA}${BOLD}проекты"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.ORANGE
                        item = ItemIcons.get("other", "book")
                        onClick { _, _, _ ->
                            ActiveProjectsMenu(player).closeAll(false).tryExecute()
                        }
                    }
                )
            }
        }
    }
}