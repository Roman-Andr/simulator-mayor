package me.slavita.construction.action.chat

import me.func.atlas.Atlas
import me.func.mod.util.command
import me.slavita.construction.prepare.GuidePrepare
import me.slavita.construction.utils.user

object UserCommands {
    init {
        command("dialog") { player, args ->
            if (args[0] != Atlas.find("dialogs").getString("command-key")) return@command

            GuidePrepare.tryNext(player.user)
        }

        command("break") { player, _ ->
            player.user.currentCity.breakStructure()
        }

        command("ok") { _, _ -> }
    }
}