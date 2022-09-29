package me.slavita.construction.structure.instance

import me.slavita.construction.world.BlockProperties
import me.slavita.construction.world.Box
import net.minecraft.server.v1_12_R1.BlockPosition
import me.slavita.construction.utils.extensions.BlocksExtensions.add
import me.slavita.construction.utils.extensions.BlocksExtensions.toLocation
import org.bukkit.Location
import org.bukkit.Material

class Structure(val name: String, val box: Box) {
    val world = box.min.world
    var blocksCount = 0
        private set

    init {
        box.forEachBukkit {
            if (it.type != Material.AIR) blocksCount++
        }
    }

    fun getNextBlock(position: BlockPosition): BlockProperties? {
        return getNextBlock(position.y * (box.dimensions.x * box.dimensions.z) + position.x * box.dimensions.z + position.z + 1)
    }

    private fun getNextBlock(blocksPassed: Int): BlockProperties? {
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
            val block = currentPosition.add(box.min).toLocation(world).block

            if (block.type != Material.AIR) return BlockProperties(currentPosition, block.type, block.data)

            blocks++
        }
    }

    fun getFirstBlock(): BlockProperties = getNextBlock(0)!!

    fun contains(location: BlockPosition): Boolean = box.contains(Location(null, location.x.toDouble() + box.min.x, location.y.toDouble()+ box.min.y, location.z.toDouble() + box.min.z))
}