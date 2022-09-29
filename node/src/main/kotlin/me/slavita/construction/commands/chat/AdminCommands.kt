package me.slavita.construction.commands.chat

import me.func.mod.util.command
import me.slavita.construction.app

object AdminCommands {
    init {
        command("money") { player, args ->
            app.getUser(player).stats.money += args[0].toLong()
        }
    }
}