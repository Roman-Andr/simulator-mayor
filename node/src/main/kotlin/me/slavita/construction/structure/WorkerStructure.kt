package me.slavita.construction.structure

import me.func.mod.util.after
import me.slavita.construction.app
import me.slavita.construction.reward.Reward
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.extensions.LoggerUtils.fine
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.GameWorld
import org.bukkit.Location
import org.bukkit.entity.Player

class WorkerStructure(
    world: GameWorld,
    structure: Structure,
    owner: Player,
    allocation: Location,
    val workers: HashSet<Worker>,
    rewards: List<Reward>
) : BuildingStructure(world, structure, owner, allocation, rewards) {
    private val delayTime: Long
        get() = (60 / workers.sumOf { it.blocksSpeed }).toLong()

    override fun enterBuilding() {
        owner.fine(app.getUser(owner).activeProjects.map{it.structure.state}.toString())
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