package me.slavita.construction.action.command.menu.general

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.dailyReward
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toTime
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.REWARDS_INFO
import me.slavita.construction.utils.mapIndexedM
import org.bukkit.entity.Player

class DailyMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return dailyReward {
                title = "Ежедневные награды"
                info = REWARDS_INFO
                val rewardTaken = data.statistics.nextTakeDailyReward > System.currentTimeMillis()
                if (rewardTaken) {
                    taken = true
                    currentDay = data.statistics.nextDay - 1
                } else {
                    taken = false
                    currentDay = data.statistics.nextDay
                }
                storage = mutableListOf(
                    "info",
                    "quest_day",
                    "quest_day_booster",
                    "quest_month",
                    "quest_week",
                    "achievements",
                    "achievements_many"
                ).mapIndexedM { index, icon ->
                    button {
                        item = Icons.get("other", icon)
                        title = if (!rewardTaken) {
                            if (index == data.statistics.nextDay) "Получить ежедневную награду" else ""
                        } else {
                            if (index == data.statistics.nextDay) "Награда через ${(data.statistics.nextTakeDailyReward - System.currentTimeMillis()).toTime()}" else ""
                        }
                    }
                }
            }
        }
    }
}