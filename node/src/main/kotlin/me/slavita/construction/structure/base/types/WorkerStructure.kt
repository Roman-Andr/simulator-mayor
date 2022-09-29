package me.slavita.construction.structure.base.types

import kotlinx.coroutines.delay
import me.func.mod.util.after
import me.slavita.construction.app
import me.slavita.construction.structure.data.Structure
import me.slavita.construction.structure.base.BuildingStructure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.Cooldown
import me.slavita.construction.world.GameWorld
import org.bukkit.Bukkit.server
import org.bukkit.Location
import org.bukkit.entity.Player

class WorkerStructure(
    world: GameWorld,
    structure: Structure,
    owner: Player,
    allocation: Location
) : BuildingStructure(world, structure, owner, allocation) {
    private val delayTime: Long
        get() = 10000L / app.getUser(owner).workers.sumOf { it.skill }

    override fun enterBuilding() {
        build()
    }

    override fun blockPlaced() {

    }

    override fun buildFinished() {

    }

    private fun build() {
        if (state != StructureState.FINISHED) after(delayTime) {
            placeCurrentBlock()
            build()
        }
    }
}