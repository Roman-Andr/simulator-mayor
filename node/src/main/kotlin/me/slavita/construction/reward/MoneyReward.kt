package me.slavita.construction.reward

import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.toMoneyIcon

class MoneyReward(val money: Long) : Reward() {
    override fun getReward(user: User) {
        user.stats.money += money
    }

    override fun toString(): String {
        return money.toMoneyIcon()
    }
}