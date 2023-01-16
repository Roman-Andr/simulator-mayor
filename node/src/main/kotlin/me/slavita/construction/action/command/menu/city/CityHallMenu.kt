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
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class CityHallMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${GREEN}${BOLD}Мэрия"
                description = "Управление мэрией"
                storage = mutableListOf(
                    button {
                        title = "${GOLD}${BOLD}Улучшить"
                        hint = "Улучшить"
                        backgroundColor = GlowColor.GREEN
                        hover = """
                            ${GREEN}При улучшении:
                              ${AQUA}Уровень: ${GRAY}${hall.level.toLevel()} ${BOLD}-> ${GREEN}${(hall.level + 1).toLevel()}
                              ${GOLD}Доход: ${GRAY}${hall.income.toIncomeIcon()} ${BOLD}-> ${GOLD}${hall.nextIncome.toIncomeIcon()}
                            
                            ${BOLD}${WHITE}Стоимость: ${GREEN}${hall.upgradePrice.toMoneyIcon()}
                        """.trimIndent()
                        item = ItemIcons.get("other", "anvil")
                        onClick { _, _, _ ->
                            hall.upgrade()
                            Anime.close(player)
                        }
                    }
                )
            }
        }
    }
}