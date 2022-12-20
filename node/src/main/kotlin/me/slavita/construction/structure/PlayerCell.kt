package me.slavita.construction.structure

import me.func.mod.util.after
import me.func.unit.Building
import me.slavita.construction.player.City
import me.slavita.construction.utils.runAsync

open class PlayerCell(val city: City, val cell: Cell, var busy: Boolean) {

    val owner = city.owner
    val id
        get() = cell.id
    val box
        get() = cell.box
    val face
        get() = cell.face

    fun updateStub() {
        if (!busy) cell.stubBuilding.show(owner.player)
    }

    fun setBusy() {
        busy = true
        cell.stubBuilding.hide(owner.player)
    }
}
