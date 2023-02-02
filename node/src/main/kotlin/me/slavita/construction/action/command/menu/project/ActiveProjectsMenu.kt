package me.slavita.construction.action.command.menu.project

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.PROJECTS_INFO
import me.slavita.construction.utils.getVault
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.size
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class ActiveProjectsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run {
            return selection {
                title = "${AQUA}${BOLD}Ваши активные проекты"
                size(4, 4)
                getVault(user, StatsType.MONEY)
                hint = ""
                info = PROJECTS_INFO
                storage = this@run.data.cities.flatMap { it.projects }.mapM {
                    button {
                        item = Icons.get("alpha", "home")
                        title = "Проект #${it.id}"
                        hover = it.toString()
                        hint = " "
                    }
                }.apply {
                    addAll(
                        this@run.data.cities.flatMap { it.cityStructures }.mapM {
                            button {
                                item = Icons.get("alpha", "home1")
                                title = "Здание ${it.structure.name}"
                                backgroundColor =
                                    if (it.state == CityStructureState.BROKEN) GlowColor.RED else GlowColor.BLUE
                                hover = it.toString()
                                hint = " "
                            }
                        }
                    )
                    if (this@run.data.hasFreelance) {
                        add(
                            button {
                                item = Icons.get("alpha", "home1")
                                title = "Фриланс ${currentFreelance!!.structure.structure.name}"
                                hover = currentFreelance!!.toString()
                                hint = " "
                            }
                        )
                    }
                }
            }
        }
    }
}
