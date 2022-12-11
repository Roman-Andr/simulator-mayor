package me.slavita.construction.action.command.menu.donate

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class DonateMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Choicer(
                title = "${GOLD}${BOLD}Платные возможности",
                description = "Кристаллики: ${player.user.criBalance.toCriMoney()}"
            ).apply {
                storage = mutableListOf(
                    button {
                        item = ItemIcons.get("other", "new_booster_1")
                        title = "${GOLD}${BOLD}Бустеры"
                        description = "${GRAY}Бустеры\n${GRAY}статистики"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.YELLOW_LIGHT
                        onClick { _, _, _ ->
                            BoostersMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        item = ItemIcons.get("other", "cup")
                        title = "${AQUA}${BOLD}Улучшения"
                        hint = "Выбрать"
                        description = "${GRAY}Удобства\n${GRAY}для игры"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        onClick { _, _, _ ->
                            AbilitiesMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        item = ItemIcons.get("other", "bag1")
                        title = "${GREEN}${BOLD}Монеты"
                        hint = "Выбрать"
                        description = "${GRAY}Пополнение\n${GRAY}баланса"
                        backgroundColor = GlowColor.GREEN
                        onClick { _, _, _ ->
                            MoneyBuyMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        item = ItemIcons.get("other", "human")
                        title = "${GREEN}${BOLD}Наборы"
                        hint = "Выбрать"
                        description = "${GRAY}Паки\n${GRAY}бустеров"
                        backgroundColor = GlowColor.GREEN_LIGHT
                        onClick { _, _, _ ->
                            BoosterPackMenu(player).closeAll(false).tryExecute()
                        }
                    }
                )
            }
        }
    }
}