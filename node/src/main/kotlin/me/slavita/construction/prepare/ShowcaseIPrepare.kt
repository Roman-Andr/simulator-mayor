package me.slavita.construction.prepare

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.market.Market
import me.slavita.construction.market.MarketsManager
import me.slavita.construction.market.showcase.Showcase
import me.slavita.construction.player.User

object ShowcaseIPrepare: IPrepare {
    override fun prepare(user: User) {
        ModTransfer()
            .json(MarketsManager.markets.map(Market::instances).flatMap { it!! }.map(Showcase::getData).toTypedArray())
            .send("showcase", user.player)
    }
}