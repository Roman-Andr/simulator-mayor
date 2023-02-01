package me.slavita.construction.world

import me.func.MetaWorld
import me.func.builder.MetaSubscriber
import me.func.mod.reactive.ReactivePlace
import me.func.unit.Building
import me.func.world.WorldMeta
import me.slavita.construction.app
import me.slavita.construction.city.CityGlows
import me.slavita.construction.city.SpeedPlaces
import me.slavita.construction.city.showcase.Showcases
import me.slavita.construction.common.utils.V2i
import me.slavita.construction.common.utils.register
import me.slavita.construction.register.NpcManager
import me.slavita.construction.structure.WorldCell
import me.slavita.construction.ui.Leaderboards
import me.slavita.construction.utils.Config
import me.slavita.construction.utils.label
import me.slavita.construction.utils.labels
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

class GameWorld(val map: WorldMeta) {
    val glows = hashSetOf<ReactivePlace>()
    val cells = arrayListOf<WorldCell>()
    var freelanceCell: WorldCell
    val emptyBlock = StructureBlock(map.world.getBlockAt(0, 0, 0))
    private val blocks = hashMapOf<UUID, HashMap<V2i, HashSet<StructureBlock>>>()

    init {
        app.mainWorld = this

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
                    user.data.cities.forEach { city ->
                        city.cityStructures.forEach { structure ->
                            buildings.add(structure.building.apply {
                                show(user.player)
                            })
                        }
                        city.cityCells.forEach { cityCell ->
                            buildings.add(cityCell.worldCell.stubBuilding)
                        }
                    }
                    buildings
                }.build()
        )


        labels("place").forEachIndexed { index, label ->
            cells.add(WorldCell(index, label).apply { allocate() })
        }

        freelanceCell = WorldCell(0, label("freelance")!!).apply { allocate() }

        Config.load {
            register(
                NpcManager,
                CityGlows,
                SpeedPlaces,
                Showcases
            )
        }
    }

    @Suppress("DEPRECATION")
    fun placeFakeBlock(player: Player, block: StructureBlock, save: Boolean = true) {
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

    fun clearBlocks(player: UUID) {
        blocks[player]?.clear()
    }
}
