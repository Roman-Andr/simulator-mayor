package me.slavita.construction.action.command.menu

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toIncomeIcon
import me.slavita.construction.ui.Formatter.toLevel
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class CityHallMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Choicer(title = "${GREEN}${BOLD}Мэрия", description = "Управление мэрией").apply {
                storage = mutableListOf(
                    button {
                        title = "${AQUA}${BOLD}Улучшить"
                        hint = "Улучшить"
                        backgroundColor = GlowColor.GREEN
                        hover = """
                            ${GREEN}При улучшении:
                              ${AQUA}Уровень: ${GRAY}${hall.level.toLevel()} ${BOLD}-> ${GREEN}${(hall.level + 1).toLevel()}
                              ${GREEN}Доход: ${GRAY}${hall.income.toIncomeIcon()} ${BOLD}-> ${GOLD}${hall.nextIncome.toIncomeIcon()}
                            
                            ${BOLD}${WHITE}Стоимость: ${GREEN}${hall.upgradePrice.toMoneyIcon()}
                        """.trimIndent()
                        item = ItemIcons.get("skyblock", "crafts")
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