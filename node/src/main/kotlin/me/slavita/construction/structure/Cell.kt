package me.slavita.construction.structure

import me.func.unit.Building
import me.func.world.Label
import me.slavita.construction.app
import me.slavita.construction.utils.log
import me.slavita.construction.world.Box
import org.bukkit.block.BlockFace
import java.util.*

class Cell(val id: Int, label: Label) {

    val face: BlockFace = try {
        BlockFace.valueOf(label.tag.uppercase())
    } catch (exception: Exception) {
        log("Illegal label: $label")
        BlockFace.WEST
    }

    val box = Box(label.clone().add(1.0, -1.0, 1.0), label.clone().add(24.0, 47.0, 24.0))
    val stubBox = me.func.world.Box(
        app.mainWorld.map,
        box.min.clone().add(0.0, -60.0, 0.0),
        box.max.clone().add(0.0, -60.0, 0.0),
        "",
        ""
    )

    val stubBuilding = Building(UUID.randomUUID(), "", "", 0.0, 0.0, 0.0, stubBox)

    init {
        stubBuilding.allocate(box.min.clone().add(12.0, 0.0, 12.0))
    }
}
