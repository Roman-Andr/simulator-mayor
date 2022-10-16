package me.slavita.construction.structure

import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.player.User
import me.slavita.construction.project.Project
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.structure.tools.StructureVisual
import me.slavita.construction.utils.extensions.BlocksExtensions.unaryMinus
import me.slavita.construction.utils.extensions.BlocksExtensions.withOffset
import me.slavita.construction.world.Box
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.StructureBlock
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Location

abstract class BuildingStructure(
    val world: GameWorld,
    val structure: Structure,
    val owner: User,
    val allocation: Location
) {
    protected var currentBlock: StructureBlock? = null
    protected var hidden = false
    private var currentProject: Project? = null
    private val visual = StructureVisual(this)
    val box = Box(allocation, structure.box.max.withOffset(-structure.box.min).withOffset(allocation).add(1.0, 1.0, 1.0))
    var state = StructureState.NOT_STARTED
    var blocksPlaced = 0

    protected abstract fun enterBuilding()

    protected abstract fun blockPlaced()

    protected abstract fun onShow()

    protected abstract fun onHide()

    abstract fun getBannerInfo(): List<Pair<String, Double>>

    fun showVisual() {
        hidden = false
        visual.show()
        onShow()
    }

    fun hideVisual() {
        hidden = true
        visual.hide()
        onHide()
    }

    fun deleteVisual() {
        hidden = true
        visual.delete()
        onHide()
    }

    fun startBuilding(project: Project) {
        state = StructureState.BUILDING
        currentBlock = structure.getFirstBlock()

        ConnectionUtil.registerWriter(owner.player.uniqueId) { packet ->
            if (packet !is PacketPlayOutBlockChange) return@registerWriter
            if (packet.block.material != Material.AIR) return@registerWriter

            if (box.contains(packet.a)) packet.a = BlockPosition(0, 0, 0)
        }
        enterBuilding()
        currentProject = project
        visual.start()
    }

    fun placeCurrentBlock() {
        if (state != StructureState.BUILDING) return

        world.placeFakeBlock(owner.player, currentBlock!!.withOffset(allocation))
        currentBlock = structure.getNextBlock(currentBlock!!.position)

        blockPlaced()

        blocksPlaced++
        visual.update()
        if (currentBlock == null) {
            finishBuilding()
            return
        }
    }

    private fun finishBuilding() {
        state = StructureState.FINISHED
        deleteVisual()
        visual.finishShow()
    }

    fun claimed() {
        visual.hideFinish()
        state = StructureState.REWARD_CLAIMED
    }
}