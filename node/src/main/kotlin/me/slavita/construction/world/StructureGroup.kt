package me.slavita.construction.world

import dev.implario.bukkit.world.V3
import me.slavita.construction.app
import org.bukkit.Location

class StructureGroup(val name: String, val labelId: String, val count: Int) {
    val structures = arrayListOf<StructureProperties>()
    val dimensions = V3.of(25.0, 50.0, 25.0)

    init {
        val label = app.structureMap.getLabels("group", labelId)[0]
        var startX = label.x
        var startY = label.y
        var startZ = label.z
        (1 .. count).forEach { _ ->
            val minLocation = Location(app.structureMap.world, startX - dimensions.x + 2, startY - dimensions.y + 2, startZ - dimensions.z + 2)
            val maxLocation = Location(app.structureMap.world, startX - 1, startY - 1, startZ - 1)
            structures.add(StructureProperties(name, Box(minLocation, maxLocation)))

            startX += dimensions.x
            startY += dimensions.y
            startZ += dimensions.z
        }
    }
}