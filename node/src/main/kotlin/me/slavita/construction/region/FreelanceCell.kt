package me.slavita.construction.region

import me.slavita.construction.action.menu.city.LeaveFreelanceConfirm
import me.slavita.construction.player.User

class FreelanceCell(user: User, worldCell: WorldCell) : Cell(user, worldCell) {

    override fun onEnter() {
        if (child == null) changeChild(FreelanceStructure(Regions.structures.values.first(), this)) //todo: make random
    }

    override fun onLeave() {
        LeaveFreelanceConfirm(user.player).tryExecute()
    }
}
