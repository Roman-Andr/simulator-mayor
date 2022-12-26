package me.slavita.construction.action.command.menu.city

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.player.City
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.PlayerExtensions.accept
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class BuyCityConfirm(player: Player, val city: City, val fromMenu: Boolean = true) : MenuCommand(player, 10) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Confirmation(
                text = listOf(
                    "${AQUA}Купить локацию",
                    city.title,
                    "${AQUA}за ${city.price.toMoneyIcon()}",
                )
            ) {
                player.user.tryPurchase(city.price) {
                    city.unlocked = true
                    player.accept("Вы успешно купили локацию ${GOLD}${city.title}")
                    if (fromMenu) LocationsMenu(player).tryExecute()
                    else currentCity = city
                }
            }
        }
    }
}