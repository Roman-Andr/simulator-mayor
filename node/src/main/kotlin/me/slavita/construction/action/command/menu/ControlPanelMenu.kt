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
            return Choicer(title = "Меню", description = "Выбери нужный раздел").apply {
                storage = mutableListOf(
                    button {
                        title = "${BOLD}Работники"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE_MIDDLE
                        item = ItemIcons.get("other", "guild_members")
                        onClick { _, _, _ ->
                            WorkerTeamMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "${BOLD}Покупка\nработников"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        item = ItemIcons.get("other", "guild_members_add")
                        onClick { _, _, _ ->
                            BuyLootboxMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "${BOLD}Локации"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.GREEN_MIDDLE
                        item = ItemIcons.get("alpha", "islands")
                        onClick { _, _, _ ->
                            LocationsMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "${BOLD}Активные\nпроекты"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE
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