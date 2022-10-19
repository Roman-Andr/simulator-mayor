package me.slavita.construction.action.command.menu.bank

import me.func.mod.conversation.ModTransfer
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.entity.Player

class BankMainMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Selection(title = "Банк", rows = 4, columns = 3,
                storage = mutableListOf(
                    button {
                        title = "Информация"
                        hint = "Выбрать"
                        item = ItemIcons.get("skyblock", "spawn")
                        onClick { _, _, _ ->

                        }
                    },
                    button {
                        title = "Взять кредит"
                        description = "Нажмите для\nвзятия кредита"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "add")
                        onClick { _, _, _ ->
                            ModTransfer().send("bank:open", player)
                        }
                    },
                    button {
                        title = "Мои кредиты"
                        description = "Нажмите для просмотра ваших кредитов"
                        hint = "Выбрать"
                        item = ItemIcons.get("other", "quest_month")
                        onClick { _, _, _ ->
                            CreditsListMenu(player).closeAll(false).tryExecute()
                        }
                    })
            )
        }
    }
}