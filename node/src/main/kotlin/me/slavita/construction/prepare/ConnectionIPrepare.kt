package me.slavita.construction.prepare

import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.player.User
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange

object ConnectionIPrepare: IPrepare {
    override fun prepare(user: User) {
        ConnectionUtil.createChannel(user.player)

        ConnectionUtil.registerWriter(user.player.uniqueId) { packet ->
            if (packet !is PacketPlayOutBlockChange) return@registerWriter
            if (packet.block.material != Material.AIR) return@registerWriter

            packet.a = BlockPosition(0, 0, 0)
        }
    }
}