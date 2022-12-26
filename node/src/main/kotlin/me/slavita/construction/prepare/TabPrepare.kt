package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.utils.handle
import me.slavita.construction.utils.sendPacket
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo
import org.bukkit.Bukkit

object TabPrepare : IPrepare {
    override fun prepare(user: User) {
        Bukkit.getOnlinePlayers().forEach { current ->
            user.player.hidePlayer(app, current.player)
            user.player.sendPacket(
                PacketPlayOutPlayerInfo(
                    PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                    current.handle
                )
            )
            current.hidePlayer(app, user.player)
            current.sendPacket(
                PacketPlayOutPlayerInfo(
                    PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                    user.player.handle
                )
            )
        }
    }
}