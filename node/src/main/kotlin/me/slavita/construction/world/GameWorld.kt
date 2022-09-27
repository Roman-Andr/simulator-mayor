package me.slavita.construction.world

import me.func.mod.util.after
import me.func.mod.util.command
import me.func.world.WorldMeta
import me.slavita.construction.structure.client.ClientStructure
import me.slavita.construction.structure.Structure
import me.slavita.construction.structure.Structures
import org.bukkit.Location
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import java.util.*

class GameWorld(val map: WorldMeta) : Listener {

    private val structures = hashMapOf<Structure, Structure>()
    private val clientStructures = hashMapOf<UUID, ArrayList<ClientStructure>>()
    val testStructure = Structures.structureGroups[2].structures[0]

    @EventHandler
    fun InventoryClickEvent.handle() = updateFramesColor(whoClicked)

    @EventHandler
    fun PlayerItemHeldEvent.handle() = updateFramesColor(player)

    @EventHandler
    fun InventoryInteractEvent.handle() = updateFramesColor(whoClicked)

    init {
        structures[testStructure] = Structure("", testStructure.box)

        command("start") { player, _ ->
            if (clientStructures[player.uniqueId] == null) clientStructures[player.uniqueId] = arrayListOf()

            val clientStructure = ClientStructure(this, structures[testStructure]!!, player, map.getLabels("default", "1")[0])
            clientStructures[player.uniqueId]!!.add(clientStructure)
            clientStructure.startBuilding()
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

    private fun updateFramesColor(player: HumanEntity) {
        clientStructures[player.uniqueId]?.forEach {
            it.updateFrameColor()
        }
    }

    fun getSpawn() = map.label("spawn")

    fun getNpcLabels() = map.labels("npc")

    fun getShopBox() = map.box("shop", "1")

}
