package me.slavita.construction.reward

import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toExp
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.utils.accept

class ExperienceReward(private val experience: Long) : Reward {
    override fun getReward(user: User) {
        val value = experience.applyBoosters(BoosterType.EXP_BOOSTER)
        user.addExp(value)
        user.player.accept("Вы получили ${value.toMoney() + " опыта"}")
    }

    override fun toString(): String {
        return experience.applyBoosters(BoosterType.EXP_BOOSTER).toExp()
    }
}