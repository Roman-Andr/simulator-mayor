package me.slavita.construction.listener

import me.func.Lock
import me.slavita.construction.app
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.player.UserSaver
import me.slavita.construction.utils.handle
import me.slavita.construction.utils.listener
import me.slavita.construction.utils.scheduler
import me.slavita.construction.utils.sendPacket
import me.slavita.construction.utils.user
import me.slavita.construction.utils.userOrNull
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerQuitEvent
import java.util.concurrent.TimeUnit

object OnLeave : IRegistrable {
    override fun register() {
        listener<PlayerQuitEvent> {
            if (player.user.showcaseMenuTaskId != 0) scheduler.cancelTask(player.user.showcaseMenuTaskId)
            Bukkit.getOnlinePlayers().forEach { current ->
                current.hidePlayer(app, player)
                current.sendPacket(
                    PacketPlayOutPlayerInfo(
                        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                        player.handle
                    )
                )
            }

            if (player.userOrNull != null) {
                app.run {
                    UserSaver.trySaveUser(player)
                    mainWorld.clearBlocks(player.uniqueId)
                }
            }

            Lock.lock("construction-${player.uniqueId}", 10, TimeUnit.SECONDS)
        }
    }
}
