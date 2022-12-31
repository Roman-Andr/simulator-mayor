package me.slavita.construction.action.command.menu.bank

import me.func.mod.conversation.ModTransfer
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class BankMainMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${GOLD}${BOLD}Банк"
                description = "Выбери нужный раздел"
                storage = mutableListOf(
                    button {
                        title = "Взять кредит"
                        description = "Нажмите для\nвзятия кредита"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "add")
                        onClick { _, _, _ ->
                            ModTransfer()
                                .integer((player.user.data.statistics.money).toString().length)
                                .send("bank:open", player)
                        }
                    },
                    button {
                        title = "Мои кредиты"
                        description = "Нажмите для\nпросмотра ваших\nкредитов"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "quest_month")
                        onClick { _, _, _ ->
                            CreditsListMenu(player).keepHistory().tryExecute()
                        }
                    }
                )
            }
        }
    }
}