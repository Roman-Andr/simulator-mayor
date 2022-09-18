package me.slavita.construction.util

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.server.v1_12_R1.MinecraftServer
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