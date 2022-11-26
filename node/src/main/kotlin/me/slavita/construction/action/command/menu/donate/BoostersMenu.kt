package me.slavita.construction.action.command.menu.donate

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.dontate.AbilityDonate
import me.slavita.construction.dontate.BoosterDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class BoostersMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return selection {
                title = "Улучшения"
                vault = Emoji.DONATE
                rows = 3
                columns = 2
                money = "Кристалликов"
                storage = Donates.values().filter { it.donate is BoosterDonate }.map {
                    button {
                        item = it.displayItem
                        title = it.donate.title
                        description = it.donate.description
                        hint = "Купить"
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