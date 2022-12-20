package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.func.core.ChunkModifyManager
import me.func.core.MutableChunk
import me.slavita.construction.app
import me.slavita.construction.player.City
import me.slavita.construction.player.User
import me.slavita.construction.utils.handle
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.sendPacket
import net.minecraft.server.v1_12_R1.*
import org.bukkit.Bukkit
import org.bukkit.GameMode

object PlayerWorldPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            if (cities.isEmpty()) {
                cities.addAll(Atlas.find("locations").getMapList("locations").map { values ->
                    City(
                        this,
                        values["id"] as String,
                        values["title"] as String,
                        (values["price"] as Int).toLong(),
                        values["id"] as String == "1"
                    )
                }.toTypedArray())
            }

            currentCity = cities.firstOrNull { it.box.contains(player.location) }!!
            player.teleport(currentCity.getSpawn())
            player.gameMode = GameMode.ADVENTURE

            currentCity.playerCells.forEach {
                runAsync(30) {
                    it.updateStub()
                }
            }

            Bukkit.getOnlinePlayers().forEach { current ->
                player.hidePlayer(app, current.player)
                player.sendPacket(
                    PacketPlayOutPlayerInfo(
                        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                        current.handle
                    )
                )
                current.hidePlayer(app, player)
                current.sendPacket(
                    PacketPlayOutPlayerInfo(
                        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                        player.handle
                    )
                )
            }

            app.mainWorld.glows.forEach { it.send(player) }
            player.walkSpeed = data.statistics.speed
        }
    }
}
