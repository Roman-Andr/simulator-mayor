package me.slavita.construction.structure

import me.func.mod.util.after
import me.slavita.construction.player.User
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.extensions.LoggerUtils.fine
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.GameWorld
import org.bukkit.Location

class WorkerStructure(
    world: GameWorld,
    structure: Structure,
    owner: User,
    allocation: Location,
    val workers: HashSet<Worker> = hashSetOf()
) : BuildingStructure(world, structure, owner, allocation) {
    private val delayTime: Long
        get() = (60 / workers.sumOf { it.blocksSpeed }).toLong()

    override fun enterBuilding() {
        owner.player.fine(owner.activeProjects.map{it.structure.state}.toString())
        build()
    }

    override fun onShow() { }

    override fun onHide() { }

    override fun blockPlaced() { }

    private fun build() {
        if (state != StructureState.BUILDING) return
        after(delayTime) {
            placeCurrentBlock()
            build()
        }
    }
}