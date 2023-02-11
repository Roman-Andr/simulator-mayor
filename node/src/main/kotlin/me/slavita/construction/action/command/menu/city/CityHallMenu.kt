package me.slavita.construction.action.command.menu.city

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toIncomeIcon
import me.slavita.construction.ui.Formatter.toLevel
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.CITY_HALL_INFO
import me.slavita.construction.utils.click
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player

class CityHallMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            val hall = this@user.data.hall
            return choicer {
                title = "${GREEN}${BOLD}Мэрия"
                description = "Управление мэрией"
                info = CITY_HALL_INFO
                storage = mutableListOf(
                    button {
                        title = "${GOLD}${BOLD}Улучшить"
                        hint = "Улучшить"
                        backgroundColor = GlowColor.GREEN
                        hover = """
                            ${GREEN}При улучшении:
                              ${AQUA}Уровень: ${GRAY}${hall.level.toLevel()} $BOLD-> ${GREEN}${(hall.level + 1).toLevel()}
                              ${GOLD}Доход: ${GRAY}${hall.income.toIncomeIcon()} $BOLD-> ${GOLD}${hall.nextIncome.toIncomeIcon()}
                            
                            ${BOLD}${WHITE}Стоимость: ${GREEN}${hall.upgradePrice.toMoneyIcon()}
                        """.trimIndent()
                        item = Icons.get("other", "anvil")
                        click { _, _, _ ->
                            this@user.upgradeHall()
                            Anime.close(player)
                        }
                    }
                )
            }
        }
    }
}
