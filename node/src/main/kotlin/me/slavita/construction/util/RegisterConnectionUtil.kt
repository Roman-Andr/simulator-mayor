package me.slavita.construction.util

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import me.slavita.construction.app
import net.minecraft.server.v1_12_R1.MinecraftServer
import net.minecraft.server.v1_12_R1.Packet
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player

object RegisterConnectionUtil {
    fun registerChannel(player: Player, function: (Any?) -> Unit) {
        val craftPlayer = player as CraftPlayer
        craftPlayer.handle.playerConnection.networkManager.channel.pipeline().addBefore("packet_handler", craftPlayer.name, object : ChannelDuplexHandler() {
            override fun channelRead(ctx: ChannelHandlerContext?, packet: Any?) {
                super.channelRead(ctx, packet)
                MinecraftServer.SERVER.postToMainThread {
                    function(packet)
                }
            }
        })
    }
}