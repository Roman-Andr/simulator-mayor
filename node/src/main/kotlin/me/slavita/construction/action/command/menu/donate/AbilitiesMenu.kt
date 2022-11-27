package me.slavita.construction.action.command.menu.donate

import me.func.mod.ui.menu.*
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.dontate.AbilityDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class AbilitiesMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return choicer {
                title = "${AQUA}${BOLD}Улучшения"
                description = "Кристалликов: ${player.user.criBalance.toCriMoney()}"
                storage = Donates.values().filter { it.donate is AbilityDonate }.map {
                    button {
                        item = it.displayItem
                        title = it.donate.title
                        hover = it.donate.description
                        hint = "Купить"
                        description = "Купить за ${it.donate.price.toCriMoney()}"
                        backgroundColor = it.backgroudColor
                        onClick { _, _, _ ->
                            it.donate.purchase(this@user)
                        }
                    }
                }.toMutableList()
            }
        }
    }
}