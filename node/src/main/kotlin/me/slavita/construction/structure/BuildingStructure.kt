package me.slavita.construction.structure

import me.func.mod.Anime
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.element.Banner
import me.func.protocol.data.element.MotionType
import me.func.protocol.world.marker.Marker
import me.func.protocol.world.marker.MarkerSign
import me.slavita.construction.app
import me.slavita.construction.banner.BannerInfo
import me.slavita.construction.banner.BannerUtil
import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.project.Project
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureProgressBar
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.extensions.BlocksExtensions.minus
import me.slavita.construction.utils.extensions.BlocksExtensions.unaryMinus
import me.slavita.construction.utils.extensions.BlocksExtensions.withOffset
import me.slavita.construction.world.BlockProperties
import me.slavita.construction.world.GameWorld
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

abstract class BuildingStructure(
    val world: GameWorld,
    val structure: Structure,
    val owner: Player,
    val allocation: Location
) {
    var state = StructureState.NOT_STARTED
    protected var currentBlock: BlockProperties? = null
    private val progressBar = StructureProgressBar(owner, structure.blocksCount)
    private var blocksPlaced = 0
    private var banner: Banner? = null
    private var marker: Marker? = null
    protected var hidden = false
    private var currentProject: Project? = null

    protected abstract fun enterBuilding()

    protected abstract fun blockPlaced()

    protected abstract fun buildFinished()

    protected abstract fun onShow()

    protected abstract fun onHide()

    fun show() {
        hidden = false
        progressBar.show()
        Banners.hide(owner, banner!!.uuid)
        Anime.removeMarker(owner, marker!!)
        onShow()
    }

    fun hide() {
        hidden = true
        progressBar.hide()
        Banners.show(owner, banner!!)
        Anime.marker(owner, marker!!)
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

        val center = structure.box.center.withOffset(-structure.box.min).withOffset(allocation)
        banner = BannerUtil.create(BannerInfo(
            center.clone().apply { z = allocation.z }.apply { y = allocation.y - 22.49 },
            BlockFace.UP,
            listOf(),
            16*23,
            16*23,
            GlowColor.BLUE,
            0.24,
            MotionType.CONSTANT,
            -90.0f
        ))
        marker = Marker(center.x, center.y, center.z, 80.0, MarkerSign.ARROW_DOWN)
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