package me.slavita.construction.action.chat

import me.func.atlas.Atlas
import me.func.mod.util.command
import me.func.stronghold.Stronghold
import me.slavita.construction.prepare.GuidePrepare

object UserCommands {
    init {
        command("dialog") { player, args ->
            if (args[0] != Atlas.find("dialogs").getString("command-key")) return@command

            GuidePrepare.tryNext(player)
        }
    }
}