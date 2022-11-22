package me.slavita.construction.action.chat

import me.func.atlas.Atlas
import me.func.mod.util.command
import me.slavita.construction.app
import me.slavita.construction.prepare.GuidePrepare
import me.slavita.construction.prepare.StoragePrepare

object UserCommands {
    init {
        command("dialog") { player, args ->
            if (args[0] != Atlas.find("dialogs").getString("command-key")) return@command

            GuidePrepare.tryNext(player)
        }

        command("city") { player, args ->
            val user = app.getUser(player)
            val city = user.cites[args[0].toInt()]

            player.teleport(city.getSpawn())
            user.currentCity = city

            StoragePrepare.prepare(user)

            user.currentCity.projects.forEach { it.structure.deleteVisual() }
            city.projects.forEach { it.structure.showVisual() }
        }
    }
}