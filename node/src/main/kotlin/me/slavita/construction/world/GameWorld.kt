package me.slavita.construction.world

import me.func.mod.util.command
import me.func.world.WorldMeta
import me.slavita.construction.structure.base.BuildingStructure
import me.slavita.construction.structure.base.types.ClientStructure
import me.slavita.construction.structure.base.types.WorkerStructure
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.instance.Structures
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.util.*

class GameWorld(val map: WorldMeta) : Listener {

    private val structures = hashMapOf<Structure, Structure>()
    val clientStructures = hashMapOf<UUID, ArrayList<BuildingStructure>>()
    val testStructure = Structures.structureGroups[2].structures[0]

    init {
        structures[testStructure] = Structure("", testStructure.box)

        command("start") { player, _ ->
            if (clientStructures[player.uniqueId] == null) clientStructures[player.uniqueId] = arrayListOf()

            val clientStructure = ClientStructure(this, structures[testStructure]!!, player, map.getLabels("default", "1")[0])
            clientStructures[player.uniqueId]!!.add(clientStructure)
            clientStructure.startBuilding()
        }

        command("build") { player, _ ->
            if (clientStructures[player.uniqueId] == null) clientStructures[player.uniqueId] = arrayListOf()

            val clientStructure = WorkerStructure(this, structures[testStructure]!!, player, map.getLabels("default", "1")[0])
            clientStructures[player.uniqueId]!!.add(clientStructure)
            clientStructure.startBuilding()
        }
    }

    fun playerBuild(player: Player) {
        if (clientStructures[player.uniqueId] == null) clientStructures[player.uniqueId] = arrayListOf()

        val clientStructure = ClientStructure(this, Structures.structureGroups.random().structures.random(), player, map.getLabels("default", "1")[0])
        clientStructures[player.uniqueId]!!.add(clientStructure)
        clientStructure.startBuilding()
    }

    fun playerWorkerBuild(player: Player) {
        if (clientStructures[player.uniqueId] == null) clientStructures[player.uniqueId] = arrayListOf()

        val clientStructure = WorkerStructure(this, Structures.structureGroups.random().structures.random(), player, map.getLabels("default", "2")[0])
        clientStructures[player.uniqueId]!!.add(clientStructure)
        clientStructure.startBuilding()
    }

    fun placeFakeBlock(player: Player, block: BlockProperties) {
        player.sendBlockChange(
            Location(map.world, block.position.x.toDouble(), block.position.y.toDouble(), block.position.z.toDouble()),
            block.type,
            block.data
        )
    }

    fun getSpawn() = map.label("spawn")

    fun getNpcLabels() = map.labels("npc")

    fun getShopBox() = map.box("shop", "1")

}
