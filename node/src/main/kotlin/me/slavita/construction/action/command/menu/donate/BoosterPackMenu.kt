package me.slavita.construction.action.command.menu.donate

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.dontate.BoosterPackDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class BoosterPackMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return choicer {
                title = "${GREEN}${BOLD}Игровые Наборы"
                description = "Кристаллики: ${player.user.criBalance.toCriMoney()}"
                storage = Donates.values().filter { it.donate is BoosterPackDonate }.map {
                    button {
                        item = it.displayItem
                        title = it.donate.title
                        hover = it.donate.description
                        hint = "Купить"
                        description = "Цена: ${it.donate.price.toCriMoney()}"
                        backgroundColor = it.backgroudColor
                        onClick { _, _, _ ->
                            it.donate.purchase(this@user)
                        }
                    }
                }.toMutableList()
            }
        }
    }
}