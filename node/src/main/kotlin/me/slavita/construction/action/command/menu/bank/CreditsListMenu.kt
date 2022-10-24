package me.slavita.construction.action.command.menu.bank

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.bank.Bank
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.Formatter.toTime
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.extensions.PlayerExtensions.error
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import org.bukkit.Bukkit
import org.bukkit.ChatColor.AQUA
import org.bukkit.entity.Player
import java.util.stream.Collectors
import java.util.stream.Stream

class CreditsListMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return getBaseSelection(MenuInfo("Ваши кредиты", StatsType.CREDIT, 4, 5)).apply {
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    Bank.playersData[player.uniqueId]!!.forEachIndexed { index, value ->
                        this@storage.add(
                            button {
                                item = ItemIcons.get("other", "quests")
                                title = "Кредит #$index"
                                val task = Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
                                    hover = """
                                    ${AQUA}Номер: $index
                                    ${AQUA}Величина: ${value.creditValue.toMoney()}
                                    ${AQUA}Процент: ${value.percent}%
                                    ${AQUA}Оставшееся время: ${value.timeLast.toTime()}
                                """.trimIndent()
                                }, 0L, 20L)
                                onClick { _, _, _ ->
                                    Bukkit.server.scheduler.cancelTask(task)
                                    if (this@user.stats.money > value.needToGive) {
                                        RepayCreditConfim(player, value).tryExecute()
                                    }
                                    else {
                                        player.killboard("Не хватает денег для погашения кредита")
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