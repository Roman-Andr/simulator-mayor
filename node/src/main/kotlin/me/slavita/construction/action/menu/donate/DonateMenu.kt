package me.slavita.construction.action.menu.donate

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.DONATE_INFO
import me.slavita.construction.utils.click
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class DonateMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${GOLD}${BOLD}Платные возможности"
                info = DONATE_INFO
                description = "Кристаллики: ${criBalance.toCriMoney()}"
                storage = mutableListOf(
                    button {
                        item = Icons.get("other", "bag1")
                        title = "${GREEN}${BOLD}Монеты"
                        hint = "Выбрать"
                        description = "${GRAY}Пополнение\n${GRAY}баланса"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        click { _, _, _ ->
                            MoneyBuyMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        item = Icons.get("other", "cup")
                        title = "${AQUA}${BOLD}Улучшения"
                        hint = "Выбрать"
                        description = "${GRAY}Удобства\n${GRAY}для игры"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        click { _, _, _ ->
                            AbilitiesMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        item = Icons.get("other", "new_booster_1")
                        title = "${GOLD}${BOLD}Бустеры"
                        description = "${GRAY}Бустеры\n${GRAY}статистики"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        click { _, _, _ ->
                            BoostersMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        item = Icons.get("other", "human")
                        title = "${GREEN}${BOLD}Наборы"
                        hint = "Выбрать"
                        description = "${GRAY}Паки\n${GRAY}бустеров"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        click { _, _, _ ->
                            BoosterPackMenu(player).keepHistory().tryExecute()
                        }
                    }
                )
            }
        }
    }
}
