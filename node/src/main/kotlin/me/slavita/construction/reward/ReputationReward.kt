package me.slavita.construction.reward

import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toReputation
import me.slavita.construction.utils.cursor

class ReputationReward(private val reputation: Long) : Reward {
    override fun getReward(user: User) {
        val value = reputation.applyBoosters(BoosterType.REPUTATION_BOOSTER)
        user.data.reputation += value
        user.player.cursor("+${value.toReputation()}")
    }

    override fun toString() = reputation.applyBoosters(BoosterType.REPUTATION_BOOSTER).toReputation()
}
