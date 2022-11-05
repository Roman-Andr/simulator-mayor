package me.slavita.construction.reward

import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.toReputation
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard

class ReputationReward(val reputation: Long) : Reward() {
    override fun getReward(user: User) {
        user.stats.reputation += reputation
        user.player.killboard("Вы получили ${reputation.toReputation() + " репутации"}")
    }

    override fun toString(): String {
        return reputation.toReputation()
    }
}