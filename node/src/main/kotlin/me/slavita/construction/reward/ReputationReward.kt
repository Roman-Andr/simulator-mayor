package me.slavita.construction.reward

import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toReputation
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import ru.cristalix.core.formatting.Formatting.fine

class ReputationReward(val reputation: Long) : Reward() {
    override fun getReward(user: User) {
        val value = reputation.applyBoosters(BoosterType.REPUTATION_BOOSTER)
        user.statistics.reputation += value
        user.player.killboard(fine("Вы получили ${value.toReputation() + " репутации"}"))
    }

    override fun toString(): String {
        return reputation.applyBoosters(BoosterType.REPUTATION_BOOSTER).toReputation()
    }
}