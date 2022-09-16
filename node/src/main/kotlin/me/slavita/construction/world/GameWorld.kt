package me.slavita.construction.world

import dev.implario.bukkit.world.Label
import dev.implario.bukkit.world.V3
import dev.implario.games5e.sdk.cristalix.WorldMeta
import me.func.MetaWorld
import me.func.mod.util.after
import me.func.mod.util.command
import me.slavita.construction.util.NmsConverter
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import net.minecraft.server.v1_12_R1.World
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*


class GameWorld(val map: WorldMeta) {
    private val structures = hashMapOf<UUID, Structure>()
    private val playerStructures = hashMapOf<UUID, ArrayList<UUID>>()

    init {
        command("spawn") { player, args ->
            val structure = Structure(this, player.uniqueId, Structures.SMALL_HOUSE, map.getLabels("default", "1")[0])
            addStructure(structure)
            structure.startBuilding()
        }

        command("next") { player, args ->
            val count = args[0].toInt()
            for (i in 1..count) {
                after(i * 2L) {
                    structures[playerStructures[player.uniqueId]!![0]]!!.placeNextBlock()
                }
            }
        }
    }

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
        if (playerStructures[structure.owner] == null) playerStructures[structure.owner] = arrayListOf()
        playerStructures[structure.owner]!!.add(uuid)
        return uuid
    }

    fun getSpawn(): Label = map.getLabel("spawn")
}
