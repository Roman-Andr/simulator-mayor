package me.slavita.construction.action.chat

import me.func.atlas.Atlas
import me.func.mod.util.command
import me.slavita.construction.app
import me.slavita.construction.prepare.GuidePrepare

object UserCommands {
    init {
        command("dialog") { player, args ->
            if (args[0] != Atlas.find("dialogs").getString("command-key")) return@command

            GuidePrepare.tryNext(player)
        }

        command("city") { player, args ->
            val user = app.getUser(player)
            val city = user.cities[args[0].toInt()]

            user.changeCity(city)
        }
    }
}