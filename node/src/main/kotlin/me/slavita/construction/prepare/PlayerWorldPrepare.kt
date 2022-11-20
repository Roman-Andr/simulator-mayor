package me.slavita.construction.prepare

import me.func.mod.Anime
import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.ui.SpeedPlaces
import org.bukkit.Bukkit
import org.bukkit.GameMode

object PlayerWorldPrepare : IPrepare {
    override fun prepare(user: User) {
        user.player.teleport(app.mainWorld.getSpawn())
        user.player.gameMode = GameMode.ADVENTURE
        for (current in Bukkit.getOnlinePlayers()) {
            if (current == null) continue
            user.player.hidePlayer(app, current.player)
            current.hidePlayer(app, user.player)
        }
        SpeedPlaces.glows.forEach { it.send(user.player) }
        Anime.loadTexture(user.player, "https://storage.c7x.dev/func/tycoon/arrows-no-color.png")
        user.player.walkSpeed = user.stats.speed
    }
}