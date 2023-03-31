package me.slavita.construction.region

import me.func.unit.Building
import me.slavita.construction.action.menu.project.BuildingControlMenu
import me.slavita.construction.app
import me.slavita.construction.utils.killboard
import me.slavita.construction.world.ItemProperties
import java.util.*
import kotlin.collections.HashMap

class StaticStructure(options: StructureOptions, override val cell: ClassicCell) : Structure(options, cell) {

    val building = Building(UUID.randomUUID(), "", "", 0.0, 0.0, 0.0, box.toFuncBox(app.structureMap))
    val repairBlocks: HashMap<ItemProperties, Int> = hashMapOf()
    var state = StaticStructureState.REWARD_NOT_RECEIVED

    override fun allocate() {
        building.allocate(box.min.clone().add(11.0, 0.0, 11.0))
        building.show(user.player)
        cell.worldCell.npcAction[user.uuid] = BuildingControlMenu(user.player, this)
    }

    fun tryRepair() {
        //todo: do
        state = StaticStructureState.OK
        //user.updateIncome()
        repairBlocks.clear()
        user.player.killboard("Отремонтировано")
    }

    override fun remove() {
        super.remove()
        building.hide(user.player)
        building.deallocate()
    }
}
