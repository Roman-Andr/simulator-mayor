package me.slavita.construction.prepare

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.city.showcase.Showcase
import me.slavita.construction.common.utils.SHOWCASE_INIT_CHANNEL
import me.slavita.construction.player.User

object ShowcasePrepare : IPrepare {
    override fun prepare(user: User) {
        user.showcases.forEach { it.init() }

        ModTransfer()
            .json(user.showcases.map(Showcase::getData).toTypedArray())
            .send(SHOWCASE_INIT_CHANNEL, user.player)
    }
}
