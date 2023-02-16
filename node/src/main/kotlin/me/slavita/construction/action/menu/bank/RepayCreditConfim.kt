package me.slavita.construction.action.menu.bank

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.city.bank.Bank
import me.slavita.construction.city.bank.Credit
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.accept
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class RepayCreditConfim(player: Player, val credit: Credit) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return Confirmation(
                text = listOf(
                    "Погасить кредит",
                    "выплатой в ${GOLD}${credit.needToGive.toMoneyIcon()}",
                )
            ) {
                Bank.repayCredit(this, credit.uuid) {
                    player.accept("Кредит успешно погашен!")
                }
            }
        }
    }
}
