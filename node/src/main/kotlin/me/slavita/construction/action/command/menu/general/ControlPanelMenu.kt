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
import me.slavita.construction.action.command.menu.storage.StorageUpgrade
import me.slavita.construction.action.command.menu.worker.WorkerMenu
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.getMenuInfo
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class ControlPanelMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run {
            return selection {
                title = "${GREEN}${BOLD}Меню"
                rows = 4
                columns = 4
                info = getMenuInfo()
                storage = mutableListOf(
                    button {
                        title = "${GREEN}${BOLD}Проекты"
                        description = "${YELLOW}▶ Выбрать"
                        hint = "Выбрать"
                        hover = """
                            ${DARK_GRAY}Быстрый доступ: /projects (K)
                        """.trimIndent()
                        item = Icons.get("other", "book")
                        onClick { _, _, _ ->
                            ActiveProjectsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Локации"
                        description = "${YELLOW}▶ Выбрать"
                        hint = "Выбрать"
                        hover = """
                            ${DARK_GRAY}Быстрый доступ: /locations (L)
                        """.trimIndent()
                        item = Icons.get("alpha", "islands")
                        onClick { _, _, _ ->
                            LocationsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Теги"
                        description = "${YELLOW}▶ Выбрать"
                        hint = "Выбрать"
                        hover = """
                            ${DARK_GRAY}Быстрый доступ: /tags
                        """.trimIndent()
                        item = Icons.get("other", "clothes")
                        onClick { _, _, _ ->
                            TagsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Достижения"
                        description = "${YELLOW}▶ Выбрать"
                        hint = "Выбрать"
                        hover = """
                            ${DARK_GRAY}Быстрый доступ: /achievements
                        """.trimIndent()
                        item = Icons.get("other", "achievements_many")
                        onClick { _, _, _ ->
                            AchievementsChoiceMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "$GREEN${BOLD}Настройки"
                        description = "${YELLOW}▶ Выбрать"
                        hint = "Выбрать"
                        hover = """
                            ${DARK_GRAY}Быстрый доступ: /settings
                        """.trimIndent()
                        item = Icons.get("skyblock", "is_settings")
                        onClick { _, _, _ ->
                            SettingsMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Работники"
                        description = "${YELLOW}▶ Выбрать"
                        hint = "Выбрать"
                        hover = """
                            ${DARK_GRAY}Быстрый доступ: /workers (M)
                        """.trimIndent()
                        item = Icons.get("other", "guild_members")
                        onClick { _, _, _ ->
                            WorkerMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Склад"
                        description = "${YELLOW}▶ Выбрать"
                        hint = "Выбрать"
                        hover = """
                            ${DARK_GRAY}Быстрый доступ: /storage
                        """.trimIndent()
                        item = Icons.get("other", "stock")
                        onClick { _, _, _ ->
                            StorageUpgrade(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GREEN}${BOLD}Мэрия"
                        description = "${YELLOW}▶ Выбрать"
                        hint = "Выбрать"
                        hover = """
                            ${DARK_GRAY}Быстрый доступ: /cityhall
                        """.trimIndent()
                        item = Icons.get("other", "guild_bank")
                        onClick { _, _, _ ->
                            CityHallMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GOLD}${BOLD}Фриланс"
                        description = "${YELLOW}▶ Выбрать"
                        hint = "Выбрать"
                        hover = """
                            ${DARK_GRAY}Быстрый доступ: /freelance
                        """.trimIndent()
                        item = Icons.get("skyblock", "info")
                        onClick { _, _, _ ->
                            if (user.currentFreelance == null) StartFreelanceProject(player).tryExecute()
                        }
                    }
                )
            }
        }
    }
}