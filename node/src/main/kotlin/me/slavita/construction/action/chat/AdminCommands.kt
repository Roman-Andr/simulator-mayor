package me.slavita.construction.action.chat

import me.slavita.construction.app
import me.slavita.construction.utils.ChatCommandUtils.opCommand

object AdminCommands {
    init {
        opCommand("money") { player, args ->
            app.getUser(player).stats.money += args[0].toInt()
        }
    }
}