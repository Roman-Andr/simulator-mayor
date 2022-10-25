package me.slavita.construction.reward

import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.player.User

class ExperienceReward(val experience: Int) : Reward() {
	override fun getReward(user: User) {
		user.stats.experience += experience
	}

	override fun toString(): String {
		return "$experience ${Emoji.EXP}"
	}
}