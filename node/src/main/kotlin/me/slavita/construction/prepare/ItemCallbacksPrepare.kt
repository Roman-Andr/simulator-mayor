package me.slavita.construction.prepare

import me.slavita.construction.player.User
import me.slavita.construction.ui.ItemsManager

object ItemCallbacksPrepare : IPrepare {
    override fun prepare(user: User) {
        ItemsManager.registerPlayer(user.player)
    }
}
