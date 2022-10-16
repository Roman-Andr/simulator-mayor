package me.slavita.construction.action.command.menu.bank

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.bank.Bank
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
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
                                title = "Кредит #${index}"
                                hover = Stream.of(
                                        "${AQUA}Номер: ${index}\n",
                                        "${AQUA}Величина: ${value.creditValue.toMoney()}\n",
                                        "${AQUA}Процент: ${value.percent}\n",
                                        "${AQUA}Оставшееся время: ${value.timeLast}\n",
                                    ).collect(Collectors.joining())
                                onClick { _, _, _ ->
                                    Bank.repayCredit(this@user, value.uuid) {
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