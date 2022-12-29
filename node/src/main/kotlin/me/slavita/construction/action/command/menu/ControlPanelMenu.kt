package me.slavita.construction.action.command.menu

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.city.LocationsMenu
import me.slavita.construction.action.command.menu.project.ActiveProjectsMenu
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class ControlPanelMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run {
            return choicer {
                title = "${GREEN}${BOLD}Меню"
                description = "Выбери нужный раздел"
                storage = mutableListOf(
                    button {
                        title = "${AQUA}${BOLD}Проекты"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.ORANGE
                        item = ItemIcons.get("other", "book")
                        onClick { _, _, _ ->
                            ActiveProjectsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Локации"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.GREEN
                        item = ItemIcons.get("alpha", "islands")
                        onClick { _, _, _ ->
                            LocationsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${AQUA}${BOLD}Теги"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE
                        item = ItemIcons.get("other", "clothes")
                        onClick { _, _, _ ->
                            TagsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GRAY}${BOLD}Настройки"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.NEUTRAL
                        item = ItemIcons.get("skyblock", "is_settings")
                        onClick { _, _, _ ->
                            SettingsMenu(player).keepHistory().tryExecute()
                        }
                    }
                )
            }
        }
    }
}