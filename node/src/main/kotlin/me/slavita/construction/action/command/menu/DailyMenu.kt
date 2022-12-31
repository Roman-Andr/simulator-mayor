package me.slavita.construction.action.command.menu

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.dailyReward
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class DailyMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return dailyReward {
                taken = data.statistics.nextTakeDailyReward > System.currentTimeMillis()
                title = "Ежедневные награды"
                currentDay = player.user.data.statistics.nextDay
                storage = mutableListOf(
                    button {
                        item = ItemIcons.get("other", "info")
                        title = "Получить ежедневную награду"
                    },
                    button {
                        item = ItemIcons.get("other", "quest_day")
                        title = "Получить ежедневную награду"
                    },
                    button {
                        item = ItemIcons.get("other", "quest_day_booster")
                        title = "Получить ежедневную награду"
                    },
                    button {
                        item = ItemIcons.get("other", "quest_month")
                        title = "Получить ежедневную награду"
                    },
                    button {
                        item = ItemIcons.get("other", "quest_week")
                        title = "Получить ежедневную награду"
                    },
                    button {
                        item = ItemIcons.get("other", "achievements")
                        title = "Получить ежедневную награду"
                    },
                    button {
                        item = ItemIcons.get("other", "achievements_many")
                        title = "Получить ежедневную награду"
                    }
                )
            }
        }
    }
}