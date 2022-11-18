package me.slavita.construction.structure

import me.func.mod.util.after
import me.func.world.Label
import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.player.User
import me.slavita.construction.world.Box
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Location
import org.bukkit.block.BlockFace

open class Cell(val owner: User, val id: Int, label: Label, var busy: Boolean) {
    val face: BlockFace
    val box = Box(label.clone().add(1.0, -1.0, 1.0), label.clone().add(24.0, 47.0, 24.0))

    init {
        face = try {
            BlockFace.valueOf(label.tag.uppercase())
        } catch (exception: Exception) {
            println("Illegal label: $label")
            BlockFace.WEST
        }

        after(2) {
            ConnectionUtil.registerWriter(owner.player.uniqueId) { packet ->
                if (packet !is PacketPlayOutBlockChange) return@registerWriter
                if (packet.block.material != Material.AIR) return@registerWriter

                if (box.contains(packet.a)) packet.a = BlockPosition(0, 0, 0)
            }
        }
    }
}