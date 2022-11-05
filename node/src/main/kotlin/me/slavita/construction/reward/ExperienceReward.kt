package me.slavita.construction.reward

import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.toExp
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard

class ExperienceReward(val experience: Long) : Reward() {
	override fun getReward(user: User) {
		user.addExp(experience)
		user.player.killboard("Вы получили ${experience.toMoney() + " опыта"}")
	}

	override fun toString(): String {
		return experience.toExp()
	}
}