package me.slavita.construction.connection

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.server.v1_12_R1.MinecraftServer
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList

object ConnectionUtil {
    private val readers = hashMapOf<UUID, ArrayList<(packet: Any?) -> Unit>>()
    private val writers = hashMapOf<UUID, ArrayList<(context: PacketContext, packet: Any?) -> Unit>>()

    fun createChannel(player: Player) {
        val craftPlayer = player as CraftPlayer
        readers[player.uniqueId] = arrayListOf()
        writers[player.uniqueId] = arrayListOf()

        craftPlayer.handle.playerConnection.networkManager.channel.pipeline().addBefore("packet_handler", craftPlayer.name, object : ChannelDuplexHandler() {
            override fun channelRead(ctx: ChannelHandlerContext?, packet: Any?) {
                MinecraftServer.SERVER.postToMainThread {
                    readers[player.uniqueId]!!.forEach {
                        it(packet)
                    }
                }
                super.channelRead(ctx, packet)
            }

            override fun write(ctx: ChannelHandlerContext?, packet: Any?, promise: ChannelPromise?) {
                MinecraftServer.SERVER.postToMainThread {
                    val context = PacketContext()
                    writers[player.uniqueId]!!.forEach {
                        it(context, packet)
                    }
                    if (!context.cancelled) super.write(ctx, packet, promise)
                }
            }
        })
    }

    fun registerReader(player: UUID, action: (packet: Any?) -> Unit) = readers[player]!!.add(action)

    fun registerWriter(player: UUID, action: (context: PacketContext, packet: Any?) -> Unit) = writers[player]!!.add(action)
}