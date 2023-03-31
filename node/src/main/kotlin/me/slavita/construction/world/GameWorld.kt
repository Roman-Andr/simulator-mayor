package me.slavita.construction.world

import me.func.MetaWorld
import me.func.builder.MetaSubscriber
import me.func.mod.reactive.ReactivePlace
import me.func.unit.Building
import me.func.world.Label
import me.func.world.WorldMeta
import me.slavita.construction.app
import me.slavita.construction.showcase.Showcases
import me.slavita.construction.common.utils.V2i
import me.slavita.construction.common.utils.register
import me.slavita.construction.region.*
import me.slavita.construction.ui.npc.NpcManager
import me.slavita.construction.utils.label
import me.slavita.construction.utils.labels
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

class GameWorld(map: WorldMeta) {

    val map = run {
        app.mainWorld = this
        map
    }

    val glows = hashSetOf<ReactivePlace>()
    val cells = hashSetOf<WorldCell>()
    val emptyBlock = StructureBlock(map.world.getBlockAt(0, 0, 0))
    private val blocks = hashMapOf<UUID, HashMap<V2i, HashSet<StructureBlock>>>()
    val freelanceCell = WorldCell(label("freelance-cell")!!)

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
                .buildingLoader { uuid ->
                    val user = app.getUserOrNull(uuid) ?: return@buildingLoader arrayListOf()

                    val buildings = arrayListOf<Building>()
                    user.data.cells.forEach { cell ->
                        //todo: optimize by chunks
                        //todo: bug test
                        cell.run {
                            child?.run {
                                if (this is StaticStructure) buildings.add(building)
                            }

                            if (this is ClassicCell) buildings.add(worldCell.stub)
                        }
                    }
                    buildings
                }.build()
        )

        labels("place").forEach { label ->
            cells.add(WorldCell(label).apply { allocateStub() })
        }

        register(
            NpcManager,
            SpeedPlaces,
            Showcases
        )
    }

    @Suppress("DEPRECATION")
    fun placeFakeBlock(player: Player, block: StructureBlock, save: Boolean) {
        player.sendBlockChange(
            Location(map.world, block.position.x.toDouble(), block.position.y.toDouble(), block.position.z.toDouble()),
            block.type,
            block.sourceData
        )

        if (save) {
            val chunk = V2i(block.position.x / 16, block.position.z / 16)
            blocks.getOrPut(player.uniqueId) { hashMapOf() }.getOrPut(chunk) { hashSetOf() }.add(block)
        }
    }

    fun clearCellBlocks(player: UUID, cell: WorldCell) {
        arrayOf(cell.box.min.x, cell.box.min.z).forEach { x ->
            arrayOf(cell.box.max.x, cell.box.max.z).forEach corner@{ z ->
                val chunks = blocks[player] ?: return@corner

                val chunk = V2i(x.toInt() / 16, z.toInt() / 16)
                val blocks = chunks[chunk] ?: return@corner

                chunks[chunk] = blocks.filter { cell.box.contains(it.position) }.toHashSet()
            }
        }
    }

    fun clearBlocks(player: UUID) {
        blocks[player]?.clear()
    }
}
