package me.slavita.construction.dontate

import me.slavita.construction.player.User
import me.slavita.construction.reward.MoneyReward

open class MoneyDonate(
    title: String,
    description: String,
    price: Int,
    val skipTime: Long,
) : Donate(title, description, price) {
    var incomeOnBuy: Long = 0

    override fun purchaseSuccess(user: User) {
        MoneyReward(incomeOnBuy * skipTime, false).getReward(user)
    }
}
