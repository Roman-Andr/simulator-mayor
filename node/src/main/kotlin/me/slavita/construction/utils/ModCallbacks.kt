package me.slavita.construction.utils

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.project.BuildingControlMenu
import me.slavita.construction.app
import me.slavita.construction.bank.Bank
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import org.bukkit.ChatColor.GREEN

object ModCallbacks {
    init {
        Anime.createReader("menu:open") { player, _ ->
            if (app.getUser(player).watchableProject != null) {
                BuildingControlMenu(player, app.getUser(player).watchableProject!!).tryExecute()
            }
        }

        Anime.createReader("bank:submit") { player, buff ->
            val amount = buff.getInt(0)
            player.killboard("${GREEN}Кредит на сумму ${amount.toLong().toMoneyIcon()} ${GREEN}успешно взят")
            Bank.giveCredit(app.getUser(player), amount.toLong())
        }
    }
}