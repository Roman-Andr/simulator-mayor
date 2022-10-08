package me.slavita.construction.prepare

import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.player.User

object ConnectionPrepare: Prepare {
    override fun prepare(user: User) {
        ConnectionUtil.createChannel(user.player)
    }
}