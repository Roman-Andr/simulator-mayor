package me.slavita.construction.region

import me.slavita.construction.app
import me.slavita.construction.utils.add
import me.slavita.construction.utils.toLocation
import me.slavita.construction.utils.unaryMinus
import me.slavita.construction.world.Box
import me.slavita.construction.world.ItemProperties
import me.slavita.construction.world.StructureBlock
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Material
import java.util.*
import java.util.UUID.fromString

class StructureOptions(
    val uuid: UUID,
    val name: String,
    val cost: Long,
    val income: Long,
    regionIndex: Int,
    val structureIndex: Int,
) {
    val region = Regions.regions[regionIndex]
    val location = app.structureMap.labels("group", "$regionIndex").first().add(-26.0 * (structureIndex - 1) - 1, 0.0, -1.0)!!
    var box = Box(location.clone().add(-22.0, -49.0, -22.0), location)

    val blocks = hashMapOf<ItemProperties, Int>()
    var blocksCount = 0
        private set

    init {
        box.forEachBukkit {
            val block = ItemProperties(it)
            blocks[block] = blocks.getOrDefault(block, 0) + 1
            blocksCount++
        }
    }

    fun getNextBlock(position: BlockPosition): StructureBlock? {
        return getNextBlock(position.y * (box.dimensions.x * box.dimensions.z) + position.x * box.dimensions.z + position.z + 1)
    }

    private fun getNextBlock(blocksPassed: Int): StructureBlock? {
        var blocks = blocksPassed

        while (true) {
            val dimensions = box.dimensions
            val dimensionsXZ = dimensions.x * dimensions.z

            val blocksLeft = blocks % dimensionsXZ
            val y = blocks / dimensionsXZ

            if (y > dimensions.y) return null

            val x = blocksLeft / dimensions.x
            val z = blocksLeft % dimensions.x

            val currentPosition = BlockPosition(x, y, z)
            val block = currentPosition.add(box.min).toLocation(app.structureMap.world).block

            if (block.type != Material.AIR) return StructureBlock(block).withOffset(-box.min)

            blocks++
        }
    }

    fun getFirstBlock(): StructureBlock = getNextBlock(0)!!
}
