package me.slavita.construction.connection

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*
import kotlin.reflect.KClass

object ConnectionUtil {
    private val readers = hashMapOf<UUID, HashSet<(packet: Any?) -> Unit>>()
    private val writers = hashMapOf<UUID, HashSet<(packet: Any?) -> Unit>>()

    fun createChannel(player: Player) {
        val craftPlayer = player as CraftPlayer
        readers[player.uniqueId] = hashSetOf()
        writers[player.uniqueId] = hashSetOf()

        craftPlayer.handle.playerConnection.networkManager.channel.pipeline().addBefore("packet_handler", craftPlayer.name, object : ChannelDuplexHandler() {
            override fun channelRead(ctx: ChannelHandlerContext?, packet: Any?) {
                readers[player.uniqueId]!!.forEach { action ->
                    action(packet)
                }
                super.channelRead(ctx, packet)
            }

            override fun write(ctx: ChannelHandlerContext?, packet: Any?, promise: ChannelPromise?) {
                writers[player.uniqueId]!!.forEach { action ->
                    action(packet)
                }
                super.write(ctx, packet, promise)
            }
        })
    }

    fun registerReader(player: UUID, action: (packet: Any?) -> Unit) = readers[player]!!.add(action)

    fun registerWriter(player: UUID, action: (packet: Any?) -> Unit) = writers[player]!!.add(action)
}