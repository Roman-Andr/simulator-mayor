package me.slavita.construction.reward

import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.cursor
import org.bukkit.ChatColor.*

class MoneyReward(private val money: Long) : Reward {
    override fun getReward(user: User) {
        val value = money.applyBoosters(BoosterType.MONEY_BOOSTER)
        user.data.money += value
        user.player.accept("Вы получили ${value.toMoneyIcon()}")
        user.player.cursor("${GOLD}+${value.toMoneyIcon()}")
    }

    override fun toString(): String {
        return money.applyBoosters(BoosterType.MONEY_BOOSTER).toMoneyIcon()
    }
}
