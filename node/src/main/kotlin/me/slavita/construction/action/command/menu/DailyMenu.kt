package me.slavita.construction.action.command.menu

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.dailyReward
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.reward.daily.DailyReward
import org.bukkit.entity.Player

class DailyMenu(player: Player) : MenuCommand(player) {
	override fun getMenu(): Openable {
		app.getUser(player).run user@{
			return dailyReward {
				taken = true
				title = "Ежедневные награды"
				currentDay = 1
				info = "Мега информация"
				storage = mutableListOf(
					DailyReward(true, MoneyReward(10)).button,
					DailyReward(true, MoneyReward(10)).button,
					DailyReward(true, MoneyReward(10)).button,
					DailyReward(true, MoneyReward(10)).button,
					DailyReward(true, MoneyReward(10)).button,
					DailyReward(true, MoneyReward(10)).button,
					DailyReward(true, MoneyReward(10)).button
				)
			}
		}
	}
}