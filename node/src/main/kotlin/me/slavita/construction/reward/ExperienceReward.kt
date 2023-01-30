package me.slavita.construction.reward

import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toExp
import me.slavita.construction.utils.cursor

class ExperienceReward(private val experience: Long) : Reward {
    override fun getReward(user: User) {
        val value = experience.applyBoosters(BoosterType.EXP_BOOSTER)
        user.addExp(value)
        user.player.cursor("+${value.toExp()}")
    }

    override fun toString() = experience.applyBoosters(BoosterType.EXP_BOOSTER).toExp()
}
