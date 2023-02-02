package me.slavita.construction.action.command.menu.bank

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.city.bank.Bank
import me.slavita.construction.city.bank.Credit
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.accept
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player

class RepayCreditConfim(player: Player, val credit: Credit) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return Confirmation(
                text = listOf(
                    "${AQUA}Погасить кредит",
                    "${AQUA}выплатой в ${WHITE}${credit.needToGive.toMoneyIcon()}",
                )
            ) {
                Bank.repayCredit(this, credit.uuid) {
                    player.accept("Кредит успешно погашен!")
                }
            }
        }
    }
}
