package me.slavita.construction.world

import me.func.MetaWorld
import me.func.builder.MetaSubscriber
import me.func.mod.util.after
import me.func.unit.Building
import me.func.world.WorldMeta
import me.slavita.construction.app
import me.slavita.construction.common.utils.V2i
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlock
import org.bukkit.entity.Player
import java.util.*

class GameWorld(val map: WorldMeta) {
    val blocks = hashMapOf<UUID, HashMap<V2i, HashSet<StructureBlock>>>()

    init {
        MetaWorld.universe(
            map.world,
            *MetaSubscriber()
                .customModifier { chunk ->
                    val chunks = blocks[chunk.owner] ?: return@customModifier chunk
                    val blocks = chunks[V2i(chunk.chunk.locX, chunk.chunk.locZ)] ?: return@customModifier chunk

                    blocks.forEach {
                        chunk.modify(
                            it.position,
                            (it.sourceBlock as CraftBlock).data0
                        )
                    }

                    chunk
                }
                .buildingLoader {
                    val user = app.getUserOrNull(it) ?: return@buildingLoader arrayListOf()

                    val buildings = arrayListOf<Building>()
                    user.city.cityStructures.forEach { structure ->
                        buildings.add(structure.building.apply {
                            show(user.player)
                        })
                    }
                    buildings
                }.build()
        )
        after (5) {
            map.getLabels("storage").forEach {
                map.world.getBlockAt(it.toBlockLocation()).type = Material.CHEST
            }
        }
    }

    fun placeFakeBlock(player: Player, block: StructureBlock) {
        player.sendBlockChange(
            Location(map.world, block.position.x.toDouble(), block.position.y.toDouble(), block.position.z.toDouble()),
            block.type,
            block.sourceData
        )

        val chunk = V2i(block.position.x / 16, block.position.z / 16)
        blocks.getOrPut(player.uniqueId) { hashMapOf() }.getOrPut(chunk) { hashSetOf() }.add(block)
    }

    fun getSpawn() = map.label("spawn")

    fun getNpcLabels() = map.labels("npc")
}
