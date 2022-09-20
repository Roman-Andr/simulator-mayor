package me.slavita.construction.world.market

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.app
import me.slavita.construction.world.structure.Structures
import org.bukkit.Material
import org.bukkit.entity.Player

object Market {
    private val typesSet = hashSetOf<Material>()

    init {
        Structures.structureGroups.forEach { group ->
            group.structures.forEach { structure ->
                val min = structure.box.min
                val max = structure.box.max
                (max.y.toInt() downTo min.y.toInt()).forEach { y ->
                    (max.x.toInt() downTo min.x.toInt()).forEach { x ->
                        (max.z.toInt() downTo min.z.toInt()).forEach { z ->
                            typesSet.add(app.structureMap.world.getBlockAt(x, y, z).type)
                        }
                    }
                }
            }
        }
    }

    fun sendPlayer(player: Player) {
        val chunkSize = 4
        val types = typesSet.toTypedArray().toList().chunked(chunkSize)
        var counter = 0

        app.mainWorld.getShopBox().forEachBukkit {
            if (it.block.type != Material.DIAMOND_BLOCK) return@forEachBukkit

            ModTransfer()
                .integer(it.x.toInt())
                .integer(it.y.toInt())
                .integer(it.z.toInt())
                .string(toSplitFormat(types[counter]))
                .send("market:stall")

            counter++
        }
    }

    private fun toSplitFormat(types: List<Material>): String {
        var result = ""
        types.forEach {
            result += "${it.id};"
        }
        return result
    }
}