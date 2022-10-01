package me.slavita.construction.structure

import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureProgressBar
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.extensions.BlocksExtensions.minus
import me.slavita.construction.world.BlockProperties
import me.slavita.construction.world.GameWorld
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
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
        ConnectionUtil.registerWriter(owner.uniqueId) { packet ->
            if (packet !is PacketPlayOutBlockChange) return@registerWriter
            if (packet.block.material != Material.AIR) return@registerWriter

            if (structure.contains(packet.a - allocation)) packet.a = BlockPosition(0, 0, 0)
        }
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