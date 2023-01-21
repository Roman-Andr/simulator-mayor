package me.slavita.construction.action.command.menu.donate

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.click
import me.slavita.construction.utils.getDonateInfo
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class DonateMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return choicer {
                title = "${GOLD}${BOLD}Платные возможности"
                info = getDonateInfo()
                description = "Кристаллики: ${player.user.criBalance.toCriMoney()}"
                storage = mutableListOf(
                    button {
                        item = ItemIcons.get("other", "bag1")
                        title = "${GREEN}${BOLD}Монеты"
                        hint = "Выбрать"
                        description = "${GRAY}Пополнение\n${GRAY}баланса"
                        backgroundColor = GlowColor.GREEN
                        click { _, _, _ ->
                            MoneyBuyMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        item = ItemIcons.get("other", "cup")
                        title = "${AQUA}${BOLD}Улучшения"
                        hint = "Выбрать"
                        description = "${GRAY}Удобства\n${GRAY}для игры"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        click { _, _, _ ->
                            AbilitiesMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        item = ItemIcons.get("other", "new_booster_1")
                        title = "${GOLD}${BOLD}Бустеры"
                        description = "${GRAY}Бустеры\n${GRAY}статистики"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.YELLOW_LIGHT
                        click { _, _, _ ->
                            BoostersMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        item = ItemIcons.get("other", "human")
                        title = "${GREEN}${BOLD}Наборы"
                        hint = "Выбрать"
                        description = "${GRAY}Паки\n${GRAY}бустеров"
                        backgroundColor = GlowColor.GREEN_LIGHT
                        click { _, _, _ ->
                            BoosterPackMenu(player).keepHistory().tryExecute()
                        }
                    }
                )
            }
        }
    }
}
