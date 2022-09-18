package me.slavita.construction.util

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.server.v1_12_R1.MinecraftServer
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player

object RegisterConnectionUtil {
    fun registerChannelRead(player: Player, readFunction: (packet: Any?) -> Unit, writeFunction: (packet: Any?) -> Boolean) {
        val craftPlayer = player as CraftPlayer
        val channel = craftPlayer.handle.playerConnection.networkManager.channel
        println(channel)
        channel.pipeline().addBefore("packet_handler", craftPlayer.name, object : ChannelDuplexHandler() {
            override fun channelRead(ctx: ChannelHandlerContext?, packet: Any?) {
                MinecraftServer.SERVER.postToMainThread {
                    readFunction(packet)
                }
                super.channelRead(ctx, packet)
            }

            override fun write(ctx: ChannelHandlerContext?, packet: Any?, promise: ChannelPromise?) {
                MinecraftServer.SERVER.postToMainThread {
                    if (writeFunction(packet)) super.write(ctx, packet, promise)
                }
            }
        })
    }
}