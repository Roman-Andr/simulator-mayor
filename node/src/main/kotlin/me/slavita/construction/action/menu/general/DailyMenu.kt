package me.slavita.construction.action.menu.general

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.dailyReward
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.ui.Formatter.toTime
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.REWARDS_INFO
import me.slavita.construction.utils.getDailyReward
import me.slavita.construction.utils.mapIndexedM
import org.bukkit.ChatColor
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class DailyMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return dailyReward {
                title = "Ежедневные награды"
                info = REWARDS_INFO
                val rewardTaken = data.nextTakeDailyReward > System.currentTimeMillis()
                if (rewardTaken) {
                    taken = true
                    currentDay = data.nextDay - 1
                } else {
                    taken = false
                    currentDay = data.nextDay
                }
                storage = getDailyReward(user).mapIndexedM { index, entry ->
                    button {
                        item = Icons.get("other", entry.first)
                        val displayTitle: String
                        val reward = entry.second.random()
                        if (!rewardTaken && index == data.nextDay) {
                            displayTitle = "Получить ежедневную награду"
                            user.waitingReward = reward
                        } else if (rewardTaken && index == data.nextDay - 1) {
                            displayTitle = "Награда получена"
                        } else {
                            displayTitle =
                                "Следующая награда через ${GOLD}${(data.nextTakeDailyReward - System.currentTimeMillis()).toTime()}"
                        }
                        title = "${displayTitle}\n\n${AQUA}Награда: ${ChatColor.WHITE}$reward"
                    }
                }
            }
        }
    }
}
