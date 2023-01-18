package me.slavita.construction.action.command.menu.bank

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.bank.Bank
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.getBaseSelection
import me.slavita.construction.utils.getCreditsInfo
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class CreditsListMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return getBaseSelection(MenuInfo("${GOLD}${BOLD}Ваши кредиты", StatsType.CREDIT, 4, 5), user).apply {
                info = getCreditsInfo()
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
                                onClick { _, _, _ ->
                                    if (this@user.data.statistics.money > value.needToGive) {
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