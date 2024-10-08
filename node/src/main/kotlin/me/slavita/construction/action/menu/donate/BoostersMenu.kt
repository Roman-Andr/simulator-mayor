package me.slavita.construction.action.menu.donate

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.dontate.BoosterDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.ui.Formatter
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.utils.DONATE_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.size
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class BoostersMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${GOLD}${BOLD}Бустеры"
                info = DONATE_INFO
                vault = Formatter.donateIcon
                size(3, 2)
                money = "Кристаллики: ${AQUA}$criBalance"
                storage = Donates.values().filter { it.donate is BoosterDonate }.mapM {
                    button {
                        item = it.displayItem
                        title = it.donate.title
                        hover = it.donate.description
                        description = "Цена: ${it.donate.price.toCriMoney()}"
                        hint = "Купить"
                        backgroundColor = it.backgroudColor
                        click { _, _, _ ->
                            it.donate.purchase(this@user)
                        }
                    }
                }
            }
        }
    }
}
