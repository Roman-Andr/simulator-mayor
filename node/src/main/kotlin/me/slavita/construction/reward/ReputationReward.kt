package me.slavita.construction.reward

import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.toReputation

class ReputationReward(val reputation: Long) : Reward() {
    override fun getReward(user: User) {
        user.stats.reputation += reputation
    }

    override fun toString(): String {
        return reputation.toReputation()
    }
}