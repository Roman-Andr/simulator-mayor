package me.slavita.construction.reward

import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard

class MoneyReward(val money: Long) : Reward() {
	override fun getReward(user: User) {
		user.stats.money += money
		user.player.killboard("Вы получили ${money.toMoneyIcon()}")
	}

	override fun toString(): String {
		return money.toMoneyIcon()
	}
}