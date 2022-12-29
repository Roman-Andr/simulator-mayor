package me.slavita.construction.world

import me.func.MetaWorld
import me.func.builder.MetaSubscriber
import me.func.mod.reactive.ReactivePlace
import me.func.mod.util.after
import me.func.unit.Building
import me.func.world.WorldMeta
import me.slavita.construction.app
import me.slavita.construction.common.utils.V2i
import me.slavita.construction.structure.Cell
import me.slavita.construction.utils.labels
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

class GameWorld(val map: WorldMeta) {
    private val blocks = hashMapOf<UUID, HashMap<V2i, HashSet<StructureBlock>>>()
    val glows = hashSetOf<ReactivePlace>()
    val cells = hashSetOf<Cell>()

    init {
        MetaWorld.universe(
            map.world,
            *MetaSubscriber()
                .customModifier { chunk ->
                    val chunks = blocks[chunk.owner] ?: return@customModifier chunk
                    val blocks = chunks[V2i(chunk.chunk.locX, chunk.chunk.locZ)] ?: return@customModifier chunk

                    blocks.forEach { block ->
                        chunk.modify(
                            block.position,
                            block.sourceCraftData
                        )
                    }

                    chunk
                }
                .buildingLoader { building ->
                    val user = app.getUserOrNull(building) ?: return@buildingLoader arrayListOf()

                    val buildings = arrayListOf<Building>()
                    user.cities.forEach { city ->
                        city.cityStructures.forEach { structure ->
                            buildings.add(structure.building.apply {
                                show(user.player)
                            })
                        }
                        city.playerCells.forEach { playerCell ->
                            buildings.add(playerCell.cell.stubBuilding)
                        }
                    }
                    buildings
                }.build()
        )

        after(1) {
            labels("place").forEachIndexed { index, label ->
                cells.add(Cell(index, label))
            }
        }

        /*map.world.handle.chunkInterceptor = ChunkInterceptor { chunk: Chunk, flags: Int, receiver: EntityPlayer? ->
            val player = receiver ?: return@ChunkInterceptor PacketPlayOutMapChunk(chunk, flags)

            player.uniqueID.user.cities.forEach { city ->
                city.playerCells.forEach {
                    if ((abs(it.box.min.chunk.x - chunk.locX) <= 1 && abs(it.box.min.chunk.z - chunk.locZ) <= 1) ||
                        (abs(it.box.max.chunk.x - chunk.locX) <= 1 && abs(it.box.max.chunk.z - chunk.locZ) <= 1)) {

                        runAsync(3) {
                            it.updateStub()
                        }
                    }
                }
            }

            PacketPlayOutMapChunk(chunk, flags)
        }*/
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

    fun getNpcLabels() = map.labels("npc")
}
