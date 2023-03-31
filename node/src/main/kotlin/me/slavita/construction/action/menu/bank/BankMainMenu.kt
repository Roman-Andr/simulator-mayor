package me.slavita.construction.action.menu.bank

import me.func.mod.Anime
import me.func.mod.conversation.ModTransfer
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.bank.Bank
import me.slavita.construction.common.utils.BANK_OPEN_CHANNEL
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.BANK_INFO
import me.slavita.construction.utils.DEFAULT_CREDITS_MAX_COUNT
import me.slavita.construction.utils.click
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class BankMainMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${GOLD}${BOLD}Банк"
                description = "Выбери нужный раздел"
                info = BANK_INFO
                storage = mutableListOf(
                    button {
                        title = "${GREEN}Взять кредит"
                        description = "Нажмите для\nвзятия кредита"
                        hint = "Выбрать"
                        item = Icons.get("other", "add")
                        click { _, _, _ ->
                            if (Bank.playersData[player.uniqueId]!!.size == DEFAULT_CREDITS_MAX_COUNT +
                                if (data.abilities.contains(Abilities.CREDITS_LIMIT)) 3 else 0
                            ) {
                                player.deny("Имеется максимальное количество кредитов!")
                                Anime.close(player)
                                return@click
                            }
                            ModTransfer()
                                .integer((player.user.data.money).toString().length)
                                .send(BANK_OPEN_CHANNEL, player)
                        }
                    },
                    button {
                        title = "${GREEN}Мои кредиты"
                        description = "Нажмите для\nпросмотра ваших\nкредитов"
                        hint = "Выбрать"
                        item = Icons.get("other", "quest_month")
                        click { _, _, _ ->
                            me.slavita.construction.action.menu.bank.CreditsListMenu(player).keepHistory().tryExecute()
                        }
                    }
                )
            }
        }
    }
}
