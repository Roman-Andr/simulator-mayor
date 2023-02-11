package me.slavita.construction.action.command.menu.donate

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.dontate.BoosterPackDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.utils.DONATE_INFO
import me.slavita.construction.utils.donateButton
import me.slavita.construction.utils.mapM
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class BoosterPackMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${GREEN}${BOLD}Игровые Наборы"
                info = DONATE_INFO
                description = "Кристаллики: ${criBalance.toCriMoney()}"
                storage =
                    Donates.values().filter { it.donate is BoosterPackDonate }.mapM { donateButton(it, player) }
            }
        }
    }
}
