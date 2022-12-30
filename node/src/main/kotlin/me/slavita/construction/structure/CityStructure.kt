package me.slavita.construction.structure

import me.func.unit.Building
import me.func.world.Box
import me.slavita.construction.app
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.structure.tools.CityStructureVisual
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.user
import me.slavita.construction.world.ItemProperties
import org.bukkit.entity.Player
import java.util.*

class CityStructure(val owner: Player, val structure: Structure, val playerCell: PlayerCell) {

    val box = Box(app.structureMap, structure.box.min, structure.box.max, "", "")
    val building = Building(UUID.randomUUID(), "", "", 0.0, 0.0, 0.0, box)
    var state = CityStructureState.NOT_READY
    val visual = CityStructureVisual(this)
    var repairBlocks: HashMap<ItemProperties, Int> = hashMapOf()
    var targetBlocks: HashMap<ItemProperties, Int> = structure.blocks.clone() as HashMap<ItemProperties, Int>

    init {
        building.allocate(playerCell.box.min.clone().add(11.0, 0.0, 11.0))
        building.show(owner)
        visual.update()
    }

    fun repair() {
        startIncome()
        state = CityStructureState.FUNCTIONING
        repairBlocks = hashMapOf()
        owner.accept("Здание #${playerCell.id} отремантировано")
        visual.update()
    }

    fun startIncome() {
        owner.user.income += structure.income
    }
}
