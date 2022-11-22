package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.slavita.construction.app
import me.slavita.construction.player.City
import me.slavita.construction.player.User
import me.slavita.construction.ui.SpeedPlaces
import org.bukkit.Bukkit
import org.bukkit.GameMode

object PlayerWorldPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            cities = Atlas.find("locations").getMapList("locations").map { values ->
                City(this, values["id"] as String, values["title"] as String)
            }.toTypedArray()
            currentCity = cities.firstOrNull { return@firstOrNull it.box.contains(player.location) } ?: cities[0]
            player.teleport(currentCity.getSpawn())
            player.gameMode = GameMode.ADVENTURE
            for (current in Bukkit.getOnlinePlayers()) {
                if (current == null) continue
                player.hidePlayer(app, current.player)
                current.hidePlayer(app, player)
            }
            SpeedPlaces.glows.forEach { it.send(player) }
            player.walkSpeed = stats.speed
        }
    }
}