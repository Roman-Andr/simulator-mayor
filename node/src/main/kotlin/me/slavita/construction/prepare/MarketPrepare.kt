package me.slavita.construction.prepare

import me.slavita.construction.market.Market
import me.slavita.construction.market.MarketsManager
import me.slavita.construction.player.User

object MarketPrepare : IPrepare {
    override fun prepare(user: User) {
        MarketsManager.markets[user.player.uniqueId] = Market()
    }
}