package me.slavita.construction.action.command.menu.project

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import org.bukkit.ChatColor.AQUA
import org.bukkit.entity.Player

class ActiveProjectsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return getBaseSelection(MenuInfo("Ваши активные проекты", StatsType.MONEY, 4, 5)).apply {
                hint = ""
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    city.projects.forEach {
                        this@storage.add(
                            button {
                                item = ItemIcons.get("skyblock", "settings")
                                title = "Проект #${it.id}"
                                hover = "${AQUA}ID: ${it.id}\n" +
										"${AQUA}Награды:\n" +
										it.rewards.joinToString("\n") { it.toString() }
                                special(it.structure is ClientStructure)
                                onClick { _, _, _ ->
                                    Anime.close(player)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}