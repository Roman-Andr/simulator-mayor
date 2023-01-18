package me.slavita.construction.reward

import me.slavita.construction.player.User

interface Reward {
    fun getReward(user: User)

    override fun toString(): String
}
