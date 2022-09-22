package me.slavita.construction.world

import dev.implario.bukkit.world.Box
import dev.implario.bukkit.world.Label
import dev.implario.games5e.sdk.cristalix.WorldMeta
import me.func.mod.util.after
import me.func.mod.util.command
import me.slavita.construction.world.structure.client.ClientStructure
import me.slavita.construction.world.structure.Structure
import me.slavita.construction.world.structure.StructureProperties
import me.slavita.construction.world.structure.Structures
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import java.util.*


class GameWorld(val map: WorldMeta) : Listener {
    private val structures = hashMapOf<StructureProperties, Structure>()
    private val clientStructures = hashMapOf<UUID, ArrayList<ClientStructure>>()
    val testStructure = Structures.structureGroups[0].structures[0]

    @EventHandler
    fun InventoryClickEvent.handle() {
        clientStructures[whoClicked.uniqueId]?.forEach {
            it.updateColor()
        }
    }

    @EventHandler
    fun PlayerItemHeldEvent.handle() {
        clientStructures[player.uniqueId]?.forEach {
            it.updateColor()
        }
    }

    init {
        structures[testStructure] = Structure(testStructure)

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

    fun getSpawn(): Label = map.getLabel("spawn")

    fun getNpcLabels(): List<Label> = map.getLabels("npc")

    fun getShopBox(): Box = map.getBox("shop", "1")

}
