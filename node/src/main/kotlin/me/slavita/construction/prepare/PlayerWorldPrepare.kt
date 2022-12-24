package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.slavita.construction.app
import me.slavita.construction.player.City
import me.slavita.construction.player.User
import me.slavita.construction.utils.handle
import me.slavita.construction.utils.sendPacket
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo
import org.bukkit.Bukkit
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