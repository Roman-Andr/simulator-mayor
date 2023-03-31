package me.slavita.construction.action.menu.city

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.region.FreelanceCell
import me.slavita.construction.utils.deny
import org.bukkit.ChatColor.AQUA
import org.bukkit.entity.Player

class LeaveFreelanceConfirm(player: Player) : MenuCommand(player, 10) {
    override fun getMenu(): Openable {
        user.run user@{
            return Confirmation(
                text = listOf(
                    "${AQUA}Отменить заказ",
                    "${AQUA}Вы потеряете 100 репутации",
                )
            ) {
                data.cells.first { it is FreelanceCell }.changeChild(null)
                data.reputation -= 100
                player.deny("Вы вышли во время фриланс заказа. Штраф: 100 репутации")
            }
        }
    }
}
