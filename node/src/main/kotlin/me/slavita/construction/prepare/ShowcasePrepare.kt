package me.slavita.construction.prepare

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.player.User
import me.slavita.construction.city.showcase.Showcase

object ShowcasePrepare : IPrepare {
    override fun prepare(user: User) {
        user.showcases.forEach { it.init() }

        ModTransfer()
            .json(user.showcases.map(Showcase::getData).toTypedArray())
            .send("showcase:initialize", user.player)
    }
}
