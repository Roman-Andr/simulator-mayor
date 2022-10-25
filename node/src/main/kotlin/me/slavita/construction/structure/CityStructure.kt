package me.slavita.construction.structure

import me.func.unit.Building
import me.func.world.Box
import me.slavita.construction.app
import me.slavita.construction.structure.instance.Structure
import org.bukkit.entity.Player
import java.util.*

class CityStructure(val owner: Player, val structure: Structure, val cell: Cell) {

	val box = Box(app.structureMap, structure.box.min, structure.box.max, "", "")
	val building = Building(UUID.randomUUID(), "", "", 0.0, 0.0, 0.0, box)

	init {
		building.allocate(cell.box.min.clone().add(11.0, 0.0, 11.0))
		building.show(owner)
	}
}