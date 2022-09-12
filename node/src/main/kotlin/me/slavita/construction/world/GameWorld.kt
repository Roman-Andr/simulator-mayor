package me.slavita.construction.world

import dev.implario.bukkit.world.Label
import dev.implario.games5e.sdk.cristalix.WorldMeta
import me.func.MetaWorld
import me.func.builder.MetaSubscriber
import me.func.unit.Building
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class GameWorld(val map: WorldMeta) {
    private val structures = mutableMapOf<UUID, Structure>()

    init {
        MetaWorld.universe(
            map.world, *MetaSubscriber()
                .buildingLoader {
                    val buildings = arrayListOf<Building>()
                    structures.forEach { structure -> if (structure.value.owner == it) buildings.add(structure.value.building) }
                    buildings
                }
                .build()
        )
    }

    fun showAll(player: Player) {
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
