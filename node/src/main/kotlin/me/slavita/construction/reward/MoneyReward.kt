package me.slavita.construction.reward

import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import ru.cristalix.core.formatting.Formatting.fine

class MoneyReward(val money: Long) : Reward() {
    override fun getReward(user: User) {
        val value = money.applyBoosters(BoosterType.MONEY_BOOSTER)
        user.statistics.money += value
        user.player.killboard(fine("Вы получили ${value.toMoneyIcon()}"))
    }

    override fun toString(): String {
        return money.applyBoosters(BoosterType.MONEY_BOOSTER).toMoneyIcon()
    }
}