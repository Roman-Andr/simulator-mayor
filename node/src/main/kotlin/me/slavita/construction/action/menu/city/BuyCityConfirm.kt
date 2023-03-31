package me.slavita.construction.action.menu.city

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.region.RegionOptions
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.accept
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class BuyRegionConfirm(player: Player, val region: RegionOptions, val fromMenu: Boolean = true) : MenuCommand(player, 10) {
    override fun getMenu(): Openable {
        user.run user@{
            return Confirmation(
                text = listOf(
                    "Купить регион",
                    "${AQUA}${region.name}",
                    "за 8",//todo region.price.toMoneyIcon()
                )
            ) {
                //todo
                /*tryPurchase(city.price) {
                    city.unlocked = true
                    player.accept("Вы успешно купили регион ${GOLD}${region.name}")
                    if (fromMenu) LocationsMenu(player).tryExecute()
                    else currentCity = city
                }*/
            }
        }
    }
}
