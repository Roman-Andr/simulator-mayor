package me.slavita.construction.structure

import me.func.mod.util.after
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.GameWorld
import org.bukkit.Location
import org.bukkit.entity.Player

class WorkerStructure(
    world: GameWorld,
    structure: Structure,
    owner: Player,
    allocation: Location,
    val workers: HashSet<Worker>
) : BuildingStructure(world, structure, owner, allocation) {
    private val delayTime: Long
        get() = (structure.blocksCount / (workers.sumOf { it.level }*1.4)).toLong()

    override fun enterBuilding() {
        build()
    }

    override fun onShow() { }

    override fun onHide() { }

    override fun blockPlaced() { }

    override fun buildFinished() { }

    private fun build() {
        if (state != StructureState.BUILDING) return
        after(delayTime) {
            placeCurrentBlock()
            build()
        }
    }
}