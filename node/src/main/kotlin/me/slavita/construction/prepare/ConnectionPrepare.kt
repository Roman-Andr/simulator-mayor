package me.slavita.construction.prepare

import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.player.User

object ConnectionPrepare : IPrepare {
    override fun prepare(user: User) {
        ConnectionUtil.createChannel(user.player)
    }
}