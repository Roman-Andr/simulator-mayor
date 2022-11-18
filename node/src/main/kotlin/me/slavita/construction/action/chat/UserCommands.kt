package me.slavita.construction.action.chat

import me.func.mod.util.command
import me.func.stronghold.Stronghold

object UserCommands {
    init {
        command("thx") { player, _ ->
            Stronghold.boosters.keys.forEach {
                player.performCommand("/func:thanks $it")
            }
        }
    }
}