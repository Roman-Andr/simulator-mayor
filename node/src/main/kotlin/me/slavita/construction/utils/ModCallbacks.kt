package me.slavita.construction.utils

import me.func.mod.Anime
import me.func.mod.ui.Alert
import me.func.protocol.math.Position
import me.slavita.construction.action.command.menu.project.BuildingControlMenu
import me.slavita.construction.app
import me.slavita.construction.bank.Bank
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.ChatColor.GREEN
import ru.cristalix.core.realm.IRealmService
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

        app.server.scheduler.scheduleSyncRepeatingTask(
            app,
            {
                Bukkit.getOnlinePlayers().forEach { player ->
                    Anime.overlayText(player, Position.BOTTOM_RIGHT, "Онлайн ${ChatColor.DARK_GRAY}» ${ChatColor.GOLD}" + IRealmService.get().getOnlineOnRealms("SLVT").toString())
                }
            }, 0, 5 * 20)
    }
}