package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.slavita.construction.app
import me.slavita.construction.player.City
import me.slavita.construction.player.User
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer

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

            Bukkit.getOnlinePlayers().forEach { current ->
                player.hidePlayer(app, current.player)
                (player as CraftPlayer).handle.playerConnection.sendPacket(
                    PacketPlayOutPlayerInfo(
                        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                        (current as CraftPlayer).handle
                    )
                )
                current.hidePlayer(app, player)
                current.handle.playerConnection.sendPacket(
                    PacketPlayOutPlayerInfo(
                        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                        (player as CraftPlayer).handle
                    )
                )
            }


            app.mainWorld.glows.forEach { it.send(player) }
            player.walkSpeed = statistics.speed
        }
    }
}