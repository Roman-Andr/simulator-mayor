package me.slavita.construction.listener

import me.func.Lock
import me.slavita.construction.app
import me.slavita.construction.utils.*
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerQuitEvent
import java.util.concurrent.TimeUnit

object OnLeave {
    init {
        listener<PlayerQuitEvent> {
            Bukkit.getOnlinePlayers().forEach { current ->
                current.hidePlayer(app, player)
                current.sendPacket(
                    PacketPlayOutPlayerInfo(
                        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                        player.handle
                    )
                )
            }
            Lock.lock("construction-${player.uniqueId}", 10, TimeUnit.SECONDS)
            scheduler.cancelTask(player.user.taskId)
            scheduler.cancelTask(player.user.showcaseTaskId)
            player.user.cities.forEach { city ->
                scheduler.cancelTask(city.taskId)
            }
        }
    }
}