package me.slavita.construction.listener

import me.func.Lock
import me.func.mod.util.after
import me.slavita.construction.app
import me.slavita.construction.utils.*
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerQuitEvent
import java.util.concurrent.TimeUnit

object OnLeave {
    init {
        listener<PlayerQuitEvent> {
            if (player.user.currentFreelance != null) player.inventory.storageContents =
                player.user.currentFreelance!!.playerInventory

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

            after(5) {
                app.users.remove(player.uniqueId)
            }
        }
    }
}
