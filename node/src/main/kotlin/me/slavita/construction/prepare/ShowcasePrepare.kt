package me.slavita.construction.prepare

import me.slavita.construction.player.User

object ShowcasePrepare : IPrepare {
    override fun prepare(user: User) {
//        ModTransfer()
//            .json(MarketsManager.markets.map(Market::instances).flatMap { it!! }.map(Showcase::getData).toTypedArray())
//            .send("showcase:initialize", user.player)
    }
}