package me.slavita.construction.action.command.menu.bank

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.city.bank.Bank
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.*
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class CreditsListMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${GOLD}${BOLD}Ваши кредиты"
                rows = 4
                columns = 5
                getMoney(user, this, StatsType.CREDIT)
                info = CREDITS_INFO
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    Bank.playersData[player.uniqueId]!!.forEachIndexed { index, value ->
                        this@storage.add(
                            button {
                                item = Icons.get("other", "quests")
                                hint = "Погасить"
                                title = "Кредит #${index + 1}"
                                hover = """
                                    ${AQUA}Номер: ${index + 1}
                                    ${AQUA}К отдаче: ${value.needToGive.toMoney()}
                                    ${AQUA}Процент: ${value.percent}%
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

