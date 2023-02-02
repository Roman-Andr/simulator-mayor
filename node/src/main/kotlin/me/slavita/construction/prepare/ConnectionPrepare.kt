package me.slavita.construction.prepare

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import me.slavita.construction.player.User
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import java.util.UUID

object ConnectionPrepare : IPrepare {
    override fun prepare(user: User) {
        (Bukkit.getPlayer(user.uuid) as CraftPlayer).handle.playerConnection.networkManager.channel.pipeline()
            .addBefore(
                "packet_handler", UUID.randomUUID().toString(),
                object : ChannelDuplexHandler() {
                    override fun write(ctx: ChannelHandlerContext?, packet: Any?, promise: ChannelPromise?) {
                        if (packet !is PacketPlayOutBlockChange || packet.block.material != Material.AIR) {
                            super.write(ctx, packet, promise)
                            return
                        }

                        user.currentCity.cityCells.forEach {
                            if (it.box.contains(packet.a)) packet.a = BlockPosition(0, 0, 0)
                        }
                        super.write(ctx, packet, promise)
                    }
                }
            )
    }
}
