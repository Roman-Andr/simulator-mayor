package me.slavita.construction.action.command.menu.donate

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.dontate.AbilityDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.utils.getDonateInfo
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class AbilitiesMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${AQUA}${BOLD}Улучшения"
                description = "Кристаллики: ${criBalance.toCriMoney()}"
                info = getDonateInfo()
                storage = Donates.values().filter { it.donate is AbilityDonate }.mapM {
                    button {
                        item = it.displayItem
                        title = it.donate.title
                        if (player.user.data.abilities.contains((it.donate as AbilityDonate).ability)) {
                            hint = "Куплено"
                            backgroundColor = GlowColor.NEUTRAL
                        } else {
                            hint = "Купить"
                            hover = it.donate.description
                            backgroundColor = it.backgroudColor
                        }
                        description = "Цена: ${it.donate.price.toCriMoney()}"
                        onClick { _, _, _ ->
                            if (!player.user.data.abilities.contains(it.donate.ability)) it.donate.purchase(this@user)
                        }
                    }
                }
            }
        }
    }
}