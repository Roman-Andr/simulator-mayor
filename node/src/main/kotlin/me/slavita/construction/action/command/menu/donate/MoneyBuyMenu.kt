package me.slavita.construction.action.command.menu.donate


import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.dontate.Donates
import me.slavita.construction.dontate.MoneyDonate
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.utils.donateButton
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class MoneyBuyMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return choicer {
                title = "${GREEN}${BOLD}Игровая валюта"
                description = "Кристаллики: ${player.user.criBalance.toCriMoney()}"
                storage = Donates.values().filter { it.donate is MoneyDonate }.map {
                    val value = (this@user.income * (it.donate as MoneyDonate).skipTime).toMoney()
                    it.donate.title = it.donate.title.replace("%money%", value)
                    it.donate.description = it.donate.description.replace("%money%", value)
                    it.donate.incomeOnBuy = this@user.income
                    donateButton(it, player)
                }.toMutableList()
            }
        }
    }
}