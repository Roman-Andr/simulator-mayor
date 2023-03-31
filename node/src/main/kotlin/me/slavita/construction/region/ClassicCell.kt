package me.slavita.construction.region

import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.menu.project.StructureChoicer
import me.slavita.construction.action.menu.project.StructureRegionChoicer
import me.slavita.construction.action.menu.project.StructureTypeChoicer
import me.slavita.construction.player.User
import me.slavita.construction.utils.borderBuilder

class ClassicCell(user: User, worldCell: WorldCell, val region: Region) : Cell(user, worldCell) {

    val border = borderBuilder(worldCell.box.bottomCenter, GlowColor.NEUTRAL).alpha(100).build()

    override fun allocate() {
        super.allocate()
        if (child == null) worldCell.stub.show(user.player)
    }

    override fun onEnter() {
        border.delete(user.player)

        if (child == null) {
            StructureRegionChoicer(user.player) { region ->
                StructureChoicer(user.player, region) { structure ->
                    StructureTypeChoicer(user.player, structure, this).tryExecute()
                }.tryExecute()
            }.tryExecute()
        }
    }

    override fun onLeave() {
        border.send(user.player)
    }

    override fun changeChild(structure: Structure?) {
        super.changeChild(structure)

        worldCell.stub.apply {
            if (structure == null) show(user.player) else hide(user.player)
        }
    }

    fun changeBorder(color: GlowColor) {
        border.color = color
        border.update(user.player)
    }
}
