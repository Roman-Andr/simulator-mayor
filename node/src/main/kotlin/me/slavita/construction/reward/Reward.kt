package me.slavita.construction.reward

import me.slavita.construction.player.User
import org.bukkit.entity.Player

abstract class Reward {
    abstract fun getReward(user: User)
}
