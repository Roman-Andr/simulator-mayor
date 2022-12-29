package me.slavita.construction.prepare

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.showcase.Showcase
import me.slavita.construction.showcase.Showcases
import me.slavita.construction.player.User

object ShowcasePrepare : IPrepare {
    override fun prepare(user: User) {
        if (user.data.showcases.isEmpty()) user.data.showcases = Showcases.showcases.map { Showcase(it) }.toHashSet()
        user.data.showcases.forEach { it.init() }

        ModTransfer()
            .json(user.data.showcases.map(Showcase::getData).toTypedArray())
            .send("showcase:initialize", user.player)
    }
}