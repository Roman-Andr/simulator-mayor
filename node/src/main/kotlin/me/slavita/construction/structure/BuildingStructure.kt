package me.slavita.construction.structure

import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.structure.client.StructureProgressBar
import me.slavita.construction.structure.client.StructureSender
import me.slavita.construction.structure.client.StructureState
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
    protected val sender = StructureSender(owner)
    protected val cooldown = Cooldown(30, owner)
    protected var blocksPlaced = 0

    fun startBuilding() {
        state = StructureState.BUILDING
        currentBlock = structure.getFirstBlock()
        sender.sendBlock(currentBlock!!, allocation)
        progressBar.show()
        enterBuilding()
    }

    abstract fun enterBuilding()

    abstract fun placeCurrentBlock()

}