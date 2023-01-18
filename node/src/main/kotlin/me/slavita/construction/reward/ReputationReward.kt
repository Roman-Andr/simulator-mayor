package me.slavita.construction.reward

import me.func.mod.Anime
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toReputation
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.cursor

class ReputationReward(private val reputation: Long) : Reward {
    override fun getReward(user: User) {
        val value = reputation.applyBoosters(BoosterType.REPUTATION_BOOSTER)
        user.data.statistics.reputation += value
        user.player.accept("Вы получили ${value.toReputation() + " репутации"}")
        user.player.cursor(value.toReputation())
    }

    override fun toString(): String {
        return reputation.applyBoosters(BoosterType.REPUTATION_BOOSTER).toReputation()
    }
}