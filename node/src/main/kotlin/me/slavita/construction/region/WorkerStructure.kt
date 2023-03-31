package me.slavita.construction.region

import me.slavita.construction.action.menu.project.BuildingControlMenu
import me.slavita.construction.app
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.ItemProperties

class WorkerStructure(options: StructureOptions, override val cell: ClassicCell) : BuildingStructure(options, cell, true) {

    val storage = hashMapOf<ItemProperties, Int>()
    var workers = hashSetOf<Worker>()
        set(value) {
            value.forEach { it.parent = null }
            field = value
            field.forEach { it.parent = this }

            delayTime = calculateDelayTime()
        }

    var delayTime = calculateDelayTime()
    var lastModified = app.pass

    override fun allocate() {
        super.allocate()
        cell.worldCell.npcAction[user.uuid] = BuildingControlMenu(user.player, this)
    }

    fun build() {
        if (app.pass - lastModified < delayTime) return
        lastModified = app.pass

        val item = ItemProperties(currentBlock.type, currentBlock.data)

        if (workers.isNotEmpty()) {
            if (storage.getOrDefault(item, 0) <= 0) return

            storage[item] = storage[item]!! - 1
            placeCurrentBlock()
        }
    }

    fun calculateDelayTime(): Long {
        if (workers.isEmpty()) return Long.MAX_VALUE
        return (60 / workers.sumOf { it.blocksSpeed }).toLong()
    }
}
