package me.slavita.construction.listener

import me.slavita.construction.player.User
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class LoadUserEvent(val user: User) : PlayerEvent(user.player) {

    override fun getHandlers() = HANDLER_LIST

    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLER_LIST
        }
    }
}
