package me.slavita.construction.world

import dev.implario.bukkit.world.V3
import me.slavita.construction.app
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import org.bukkit.Location

class Structure(val properties: StructureProperties) {
    private val blocks = hashMapOf<BlockPosition, BlockProperties>()
    var firstBlock: BlockProperties? = null

    init {
        val min = properties.box.min
        val max = properties.box.max
        var previousBlock: BlockProperties? = null

        (max.blockY downTo min.blockY).forEach { y ->
            (max.blockX downTo min.blockX).forEach { x ->
                (max.blockZ downTo min.blockZ).forEach z@{ z ->
                    val block = Location(app.structureMap.world, x.toDouble(), y.toDouble(), z.toDouble()).block
                    if (block.type == Material.AIR) return@z

                    val blockProperties = BlockProperties(
                        previousBlock,
                        BlockPosition(x - min.x.toInt(), y - min.y.toInt(), z - min.z.toInt()),
                        block.type,
                        block.data
                    )

                    blocks[blockProperties.position] = blockProperties
                    previousBlock = blockProperties
                }
            }
        }

        firstBlock = previousBlock
    }

    fun contains(location: BlockPosition): Boolean = properties.box.contains(Location(app.structureMap.world, location.x.toDouble(), location.y.toDouble(), location.z.toDouble()))

    fun getBlocksCount(): Int = blocks.size
}