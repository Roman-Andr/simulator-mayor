package me.slavita.construction.connection

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*

object ConnectionUtil {
    private val writers = hashMapOf<UUID, HashSet<(packet: Any?) -> Unit>>()

    fun createChannel(player: Player) {
        if (writers[player.uniqueId] == null) {
            writers[player.uniqueId] = hashSetOf()
        }

        (player as CraftPlayer).handle.playerConnection.networkManager.channel.pipeline()
            .addBefore("packet_handler", UUID.randomUUID().toString(), object : ChannelDuplexHandler() {
                override fun write(ctx: ChannelHandlerContext?, packet: Any?, promise: ChannelPromise?) {
                    writers[player.uniqueId]!!.forEach { action ->
                        action(packet)
                    }
                    super.write(ctx, packet, promise)
                }
            })
    }

    fun registerWriter(player: UUID, action: (packet: Any?) -> Unit) = writers[player]!!.add(action)
}