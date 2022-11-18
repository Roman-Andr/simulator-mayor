package me.slavita.construction.structure.instance

import me.slavita.construction.app
import me.slavita.construction.world.Box
import org.bukkit.Location
import ru.cristalix.core.math.V3

class StructureGroup(val name: String, labelId: String, count: Int) {
    val structures = arrayListOf<Structure>()
    private val dimensions: V3 = V3(25.0, 50.0, 25.0)

    init {
        val label = app.structureMap.getLabels("group", labelId)[0]
        var startX = label.x
        val startY = label.y
        val startZ = label.z
        (1..count).forEach { _ ->
            val minLocation = Location(
                app.structureMap.world,
                startX - dimensions.x + 2,
                startY - dimensions.y + 2,
                startZ - dimensions.z + 2
            )
            val maxLocation = Location(app.structureMap.world, startX - 1, startY - 1, startZ - 1)
            structures.add(Structure(name, Box(minLocation, maxLocation)))

            startX -= dimensions.x + 1
        }
    }
}