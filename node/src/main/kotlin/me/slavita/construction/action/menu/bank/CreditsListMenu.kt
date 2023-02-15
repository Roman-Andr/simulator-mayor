package me.slavita.construction.action.menu.bank

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.city.bank.Bank
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.BANK_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.getVault
import me.slavita.construction.utils.size
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.RED
import org.bukkit.entity.Player

class CreditsListMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${GREEN}${BOLD}Ваши кредиты"
                size(4, 5)
                getVault(user, StatsType.CREDIT)
                info = BANK_INFO
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    Bank.playersData[player.uniqueId]!!.forEachIndexed { index, value ->
                        this@storage.add(
                            button {
                                item = Icons.get("other", "quests")
                                hint = "Закрыть"
                                title = "Кредит #${index + 1}"
                                hover = """
                                    Номер: ${index + 1}
                                    К отдаче: ${GOLD}${value.needToGive.toMoney()}
                                    Процент: ${RED}${value.percent}%
                                """.trimIndent()
                                click { _, _, _ ->
                                    if (this@user.data.money > value.needToGive) {
                                        RepayCreditConfim(player, value).tryExecute()
                                    } else {
                                        player.deny("Не хватает денег для погашения кредита")
                                        Anime.close(player)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
