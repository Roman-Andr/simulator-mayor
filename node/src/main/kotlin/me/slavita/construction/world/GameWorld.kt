package me.slavita.construction.world

import dev.implario.bukkit.world.Box
import dev.implario.bukkit.world.Label
import dev.implario.games5e.sdk.cristalix.WorldMeta
import me.func.mod.util.after
import me.func.mod.util.command
import me.slavita.construction.app
import me.slavita.construction.utils.extensions.LoggerUtils.fine
import me.slavita.construction.world.structure.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*


class GameWorld(val map: WorldMeta) {
    private val structures = hashMapOf<StructureProperties, Structure>()
    private val clientStructures = hashMapOf<UUID, ArrayList<ClientStructure>>()
    val testStructure = Structures.structureGroups[0].structures[0]

    init {
        structures[testStructure] = Structure(testStructure)

        command("start") { player, _ ->
            if (clientStructures[player.uniqueId] == null) clientStructures[player.uniqueId] = arrayListOf()

            val clientStructure = ClientStructure(this, structures[testStructure]!!, player, map.getLabels("default", "1")[0])
            clientStructures[player.uniqueId]!!.add(clientStructure)
            clientStructure.startBuilding()
        }

        command("show") { player, _ ->
            val types = hashSetOf<Material>()

            Structures.structureGroups.forEach { group ->
                group.structures.forEach { structure ->
                    val min = structure.box.min
                    val max = structure.box.max
                    (max.y.toInt() downTo min.y.toInt()).forEach { y ->
                        (max.x.toInt() downTo min.x.toInt()).forEach { x ->
                            (max.z.toInt() downTo min.z.toInt()).forEach { z ->
                                types.add(app.structureMap.world.getBlockAt(x, y, z).type)
                            }
                        }
                    }
                }
            }
            player.fine(types.size)
        }

        command("next") { player, args ->
            val count = args[0].toInt()
            for (i in 1..count) {
                after((i - 1) * 2L) {
                    clientStructures[player.uniqueId]!![0].placeCurrentBlock()
                }
            }
        }
    }

    fun placeFakeBlock(player: Player, block: BlockProperties) {
        player.sendBlockChange(
            Location(map.world, block.position.x.toDouble(), block.position.y.toDouble(), block.position.z.toDouble()),
            block.type,
            block.data
        )
    }

    fun getSpawn(): Label = map.getLabel("spawn")

    fun getNpcLabels(): List<Label> = map.getLabels("npc")

    fun getShopBox(): Box = map.getBox("shop", "1")

}
