package me.slavita.construction.action.command.menu.bank

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.bank.Bank
import me.slavita.construction.bank.Credit
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import org.bukkit.entity.Player

class RepayCreditConfim(player: Player, val credit: Credit) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Confirmation( text = listOf(
                "Погасить кредит",
                "выплатой в ${credit.needToGive.toMoneyIcon()}",
            )) {
                Bank.repayCredit(this, credit.uuid) {
                    player.killboard("Кредит успешно погашен!")
                }
            }
        }
    }
}