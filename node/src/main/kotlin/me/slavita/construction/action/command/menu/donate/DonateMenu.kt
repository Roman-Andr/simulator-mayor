package me.slavita.construction.action.command.menu.donate

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class DonateMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Choicer(title = "Платные возможнсоти", description = "Баланс: ${player.user.criBalance.toCriMoney()}").apply {
                storage = mutableListOf(
                    button {
                        item = ItemIcons.get("other", "achievements_many")
                        title = "Бустеры"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.YELLOW_LIGHT
                        onClick { _, _, _ ->
                            BoostersMenu(player).tryExecute()
                        }
                    },
                    button {
                        item = ItemIcons.get("other", "cup")
                        title = "Улучшения"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        onClick { _, _, _ ->
                            AbilitiesMenu(player).tryExecute()
                        }
                    },
                )
            }
        }
    }
}