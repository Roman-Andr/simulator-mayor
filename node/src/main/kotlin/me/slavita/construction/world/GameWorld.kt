package me.slavita.construction.world

import dev.implario.bukkit.world.Label
import dev.implario.bukkit.world.V3
import dev.implario.games5e.sdk.cristalix.WorldMeta
import me.func.MetaWorld
import me.slavita.construction.util.NmsConverter
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import net.minecraft.server.v1_12_R1.World
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*


class GameWorld(val map: WorldMeta) {
    private val structures = hashMapOf<UUID, Structure>()

    fun placeBlock(player: Player, block: Block, location: V3) {
        val nmsWorld: World = (map.world as CraftWorld).handle
        val packet = PacketPlayOutBlockChange(nmsWorld, BlockPosition(location.x, location.y, location.z))
        val data = NmsConverter.getNmsCopy(block).fromLegacyData(block.data.toInt())
        packet.block = data
        (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
    }

    fun showAll(player: Player) {
        MetaWorld.registerModifiers()
        structures.forEach {
            if (it.value.owner == player.uniqueId) {
                it.value.show(player)
            }
        }
    }

    fun addStructure(structure: Structure): UUID {
        val uuid = UUID.randomUUID()
        structures[uuid] = structure
        return uuid
    }

    fun getSpawn(): Label = map.getLabel("spawn")
}
