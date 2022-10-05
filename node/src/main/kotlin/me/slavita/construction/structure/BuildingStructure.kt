package me.slavita.construction.structure

import me.func.mod.world.Banners
import me.func.protocol.data.element.Banner
import me.slavita.construction.app
import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.project.Project
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureProgressBar
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.banner.BannerUtil
import me.slavita.construction.utils.extensions.BlocksExtensions.minus
import me.slavita.construction.world.BlockProperties
import me.slavita.construction.world.GameWorld
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

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
    protected var hidden = false
    protected var banners = mutableListOf<Banner>()
    protected var bannersIDs = mutableListOf<UUID>()
    private var currentProject: Project? = null

    protected abstract fun enterBuilding()

    protected abstract fun blockPlaced()

    protected abstract fun buildFinished()

    protected abstract fun onShow()

    protected abstract fun onHide()

    fun show() {
        hidden = false
        progressBar.show()
        Banners.hide(owner, *bannersIDs.toTypedArray())
        onShow()
    }

    fun hide() {
        hidden = true
        progressBar.hide()
        Banners.show(owner, *banners.toTypedArray())
        onHide()
    }

    fun startBuilding(project: Project) {
        state = StructureState.BUILDING
        currentBlock = structure.getFirstBlock()

        ConnectionUtil.registerWriter(owner.uniqueId) { packet ->
            if (packet !is PacketPlayOutBlockChange) return@registerWriter
            if (packet.block.material != Material.AIR) return@registerWriter

            if (structure.contains(packet.a - allocation)) packet.a = BlockPosition(0, 0, 0)
        }
        enterBuilding()
        currentProject = project

        banners = BannerUtil.createRectangle(
        allocation // вместо этого центр
        , 11.0).toMutableList()
        bannersIDs = banners.map { it.uuid }.toMutableList()
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
        hide()
        buildFinished()
        app.getUser(owner).apply {
            activeProjects.remove(currentProject)
            stats.totalProjects++
        }
    }
}