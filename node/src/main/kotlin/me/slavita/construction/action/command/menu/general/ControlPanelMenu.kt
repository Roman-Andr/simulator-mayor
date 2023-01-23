package me.slavita.construction.action.command.menu.general

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.achievements.AchievementsChoiceMenu
import me.slavita.construction.action.command.menu.city.CityHallMenu
import me.slavita.construction.action.command.menu.city.LocationsMenu
import me.slavita.construction.action.command.menu.project.ActiveProjectsMenu
import me.slavita.construction.action.command.menu.project.StartFreelanceProject
import me.slavita.construction.action.command.menu.city.StorageUpgrade
import me.slavita.construction.action.command.menu.worker.WorkerMenu
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.*
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class ControlPanelMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run {
            return selection {
                title = "${GREEN}${BOLD}Меню"
                rows = 4
                columns = 4
                info = MENU_INFO
                storage = mutableListOf(
                    button {
                        title = "${GREEN}${BOLD}Проекты"
                        hover = PROJECTS_MENU
                        item = Icons.get("other", "book")
                        onClick { _, _, _ ->
                            ActiveProjectsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Локации"
                        hover = LOCATIONS_MENU
                        item = Icons.get("alpha", "islands")
                        onClick { _, _, _ ->
                            LocationsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Работники"
                        hover = WORKERS_MENU
                        item = Icons.get("other", "guild_members")
                        onClick { _, _, _ ->
                            WorkerMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Теги"
                        hover = TAGS_MENU
                        item = Icons.get("other", "clothes")
                        onClick { _, _, _ ->
                            TagsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Достижения"
                        hover = ACHIEVEMENTS_MENU
                        item = Icons.get("other", "achievements_many")
                        onClick { _, _, _ ->
                            AchievementsChoiceMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Склад"
                        hover = STORAGE_MENU
                        item = Icons.get("other", "stock")
                        onClick { _, _, _ ->
                            StorageUpgrade(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Мэрия"
                        hover = CITY_HALL_MENU
                        item = Icons.get("other", "guild_bank")
                        onClick { _, _, _ ->
                            CityHallMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GOLD}${BOLD}Фриланс"
                        hover = FREELANCE_MENU
                        item = Icons.get("other", "socmedia")
                        onClick { _, _, _ ->
                            if (user.currentFreelance == null) StartFreelanceProject(player).tryExecute()
                        }
                    },
                    button {
                        title = "${GOLD}${BOLD}Ежедневные\n${GOLD}${BOLD}награды"
                        hover = REWARDS_MENU
                        item = Icons.get("skyblock", "info")
                        onClick { _, _, _ ->
                            DailyMenu(player).tryExecute()
                        }
                    },
                    button {
                        title = "${DARK_GRAY}${BOLD}Настройки"
                        hover = SETTINGS_MENU
                        item = Icons.get("skyblock", "is_settings")
                        onClick { _, _, _ ->
                            SettingsMenu(player).keepHistory().tryExecute()
                        }
                    }
                ).onEach { button ->
                    button.description = "${YELLOW}▶ Выбрать"
                    button.hint = "Выбрать"
                }
            }
        }
    }
}
