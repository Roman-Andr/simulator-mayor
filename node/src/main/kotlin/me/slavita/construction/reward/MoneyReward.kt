package me.slavita.construction.reward

import me.func.stronghold.util.withBoosters
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.cursor
import org.bukkit.ChatColor.GOLD

class MoneyReward(private val money: Long, private val withBoost: Boolean = true) : Reward {
    override fun getReward(user: User) {
        val value = money.apply { if (withBoost) applyBoosters(BoosterType.MONEY_BOOSTER) }
        user.data.addMoney(value)
        user.player.cursor("$GOLD+${value.toMoneyIcon()}")
    }

    override fun toString() = money.applyBoosters(BoosterType.MONEY_BOOSTER).toMoneyIcon()
}
