package me.slavita.construction.dontate

import me.slavita.construction.player.User

open class MoneyDonate(
    title: String,
    description: String,
    price: Int,
    val skipTime: Long,
) : Donate(title, description, price) {
    var incomeOnBuy: Long = 0

    override fun purchaseSuccess(user: User) {
        user.data.addMoney(incomeOnBuy * skipTime)
    }
}
