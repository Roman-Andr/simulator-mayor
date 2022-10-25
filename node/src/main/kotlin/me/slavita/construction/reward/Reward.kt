package me.slavita.construction.reward

import me.slavita.construction.player.User

abstract class Reward {
	abstract fun getReward(user: User)

	abstract override fun toString(): String
}
