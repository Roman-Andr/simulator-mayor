package me.slavita.construction.structure

import me.func.mod.util.after
import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.player.User
import me.slavita.construction.world.Box
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Location

open class Cell(val owner: User, val id: Int, allocation: Location, var busy: Boolean) {
    val box = Box(allocation, allocation.clone().add(23.0, 48.0, 23.0))

    init {
        after(2) {
            ConnectionUtil.registerWriter(owner.player.uniqueId) { packet ->
                if (packet !is PacketPlayOutBlockChange) return@registerWriter
                if (packet.block.material != Material.AIR) return@registerWriter

                if (box.contains(packet.a)) packet.a = BlockPosition(0, 0, 0)
            }
        }
    }
}