package me.slavita.construction.reward

import me.slavita.construction.player.User
import org.bukkit.entity.Player

class MoneyReward(val money: Int) : Reward() {
    override fun getReward(user: User) {
        user.stats.money += money
    }
}