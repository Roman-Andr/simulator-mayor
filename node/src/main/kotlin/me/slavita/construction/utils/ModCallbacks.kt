package me.slavita.construction.utils

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.project.BuildingControlMenu
import me.slavita.construction.app
import me.slavita.construction.bank.Bank
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import org.bukkit.ChatColor.GREEN
import kotlin.math.pow

object ModCallbacks {
    init {
        Anime.createReader("menu:open") { player, _ ->
            if (app.getUser(player).watchableProject != null) {
                BuildingControlMenu(player, app.getUser(player).watchableProject!!).tryExecute()
            }
        }

        Anime.createReader("bank:submit") { player, buff ->
            val amount = buff.readInt()
            val digit = buff.readInt()
            val value = (amount * 10.0.pow(digit)).toLong()
            player.killboard("${GREEN}Кредит на сумму ${value.toMoneyIcon()} ${GREEN}успешно взят")
            Bank.giveCredit(app.getUser(player), value)
        }
    }
}