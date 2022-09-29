package me.slavita.construction.structure.base

import me.slavita.construction.structure.data.Structure
import me.slavita.construction.structure.tools.StructureProgressBar
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.Cooldown
import me.slavita.construction.world.BlockProperties
import me.slavita.construction.world.GameWorld
import org.bukkit.Location
import org.bukkit.entity.Player

abstract class BuildingStructure(
    val world: GameWorld,
    val structure: Structure,
    val owner: Player,
    val allocation: Location
) {
    var state = StructureState.NOT_STARTED
    protected var currentBlock: BlockProperties? = null
    protected val progressBar = StructureProgressBar(owner, structure.blocksCount)
    protected var blocksPlaced = 0

    abstract fun enterBuilding()

    abstract fun blockPlaced()

    abstract fun buildFinished()

    fun startBuilding() {
        state = StructureState.BUILDING
        currentBlock = structure.getFirstBlock()
        progressBar.show()
        enterBuilding()
    }

    fun placeCurrentBlock() {
        if (state != StructureState.BUILDING) return

        world.placeFakeBlock(owner, currentBlock!!.withOffset(allocation))
        currentBlock = structure.getNextBlock(currentBlock!!.position)

        blockPlaced()

        blocksPlaced++
        progressBar.update(blocksPlaced)

        if (currentBlock == null) {
            finishBuilding()
            return
        }
    }

    private fun finishBuilding() {
        state = StructureState.FINISHED
        progressBar.hide()
        buildFinished()
    }
}