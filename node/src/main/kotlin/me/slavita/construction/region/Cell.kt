package me.slavita.construction.region

import me.slavita.construction.player.User

abstract class Cell(val user: User, val worldCell: WorldCell) {

    var box = worldCell.box
    var allocation = box.min
    var face = worldCell.face

    var child: Structure? = null
        private set
    val inside = false

    open fun allocate() {
        worldCell.npcAction.remove(user.uuid)
    }

    fun enter() {
        if (inside) return
        child?.enter()
        onEnter()
    }

    fun leave() {
        if (!inside) return
        child?.leave()
        onLeave()
    }

    open fun changeChild(structure: Structure?) {
        child?.remove()
        child = structure

        structure?.allocate()
    }

    abstract fun onEnter()
    abstract fun onLeave()
}
