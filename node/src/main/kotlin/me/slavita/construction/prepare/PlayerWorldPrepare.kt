package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.player.User
import org.bukkit.GameMode

object PlayerWorldPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            player.gameMode = GameMode.ADVENTURE
            player.teleport(currentCity.getSpawn())

            app.mainWorld.glows.forEach { it.send(player) }
            player.walkSpeed = data.statistics.speed
        }
    }
}