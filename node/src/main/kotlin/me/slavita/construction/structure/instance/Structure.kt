package me.slavita.construction.structure.instance

import me.slavita.construction.app
import me.slavita.construction.utils.extensions.BlocksExtensions.add
import me.slavita.construction.utils.extensions.BlocksExtensions.toLocation
import me.slavita.construction.utils.extensions.BlocksExtensions.unaryMinus
import me.slavita.construction.world.Box
import me.slavita.construction.world.ItemProperties
import me.slavita.construction.world.StructureBlock
import net.minecraft.server.v1_12_R1.BlockPosition
import org.bukkit.Material
import org.bukkit.World

class Structure(val name: String, val box: Box) {
	val world: World = box.min.world
	var blocksCount = 0
		private set

	val income = 100L

	init {
		box.forEachBukkit {
			if (it.type == Material.AIR) return@forEachBukkit

			blocksCount++
			app.allBlocks.add(ItemProperties.fromBlock(it))
		}
	}

	fun getNextBlock(position: BlockPosition): StructureBlock? {
		return getNextBlock(position.y * (box.dimensions.x * box.dimensions.z) + position.x * box.dimensions.z + position.z + 1)
	}

	fun getMaterials(): HashSet<Material> {
		return hashSetOf<Material>().apply {
			box.forEachBukkit {
				this.add(it.type)
			}
		}
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
			val block = currentPosition.add(box.min).toLocation(world).block

			if (block.type != Material.AIR) return StructureBlock(block).withOffset(-box.min)

			blocks++
		}
	}

	fun getFirstBlock(): StructureBlock = getNextBlock(0)!!
}