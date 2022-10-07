package me.slavita.construction.structure

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveProgress
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.Tricolor
import me.func.protocol.data.element.Banner
import me.func.protocol.data.element.MotionType
import me.func.protocol.math.Position
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
import org.bukkit.ChatColor
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
    private var floorBanner: Banner? = null
    private var infoBanner: Banner? = null
    private var progressWorld: ReactiveProgress? = null
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
        Banners.hide(owner, floorBanner!!)
        Banners.hide(owner, infoBanner!!)
        Anime.removeMarker(owner, marker!!)
        progressWorld!!.send(owner)
        onShow()
    }

    fun hide() {
        hidden = true
        progressBar.hide()
        Banners.show(owner, floorBanner!!)
        Banners.show(owner, infoBanner!!)
        Anime.marker(owner, marker!!)
        progressWorld!!.delete(setOf(owner))
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
        floorBanner = BannerUtil.create(BannerInfo(
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
        infoBanner = BannerUtil.create(BannerInfo(
            center.clone().apply { z = allocation.z }.apply { y = allocation.y },
            BlockFace.UP,
            listOf(
                Pair("Привет", 0.5)
            ),
            16,
            16,
            Tricolor(0, 0, 0),
            0.24,
            MotionType.CONSTANT,
            0.0f,
            true
        ))
        progressWorld = ReactiveProgress.builder()
            .position(Position.BOTTOM)
            .offsetX(allocation.x)
            .offsetY(allocation.y + 5.0)
            .offsetZ(allocation.z)
            .hideOnTab(false)
            .color(GlowColor.GREEN)
            .build()
        marker = Marker(center.x, center.y, center.z, 80.0, MarkerSign.ARROW_DOWN)
    }

    fun placeCurrentBlock() {
        if (state != StructureState.BUILDING) return

        world.placeFakeBlock(owner, currentBlock!!.withOffset(allocation))
        currentBlock = structure.getNextBlock(currentBlock!!.position)

        blockPlaced()

        blocksPlaced++
        progressBar.update(blocksPlaced)
        progressWorld!!.apply {
            progress = blocksPlaced.toDouble() / structure.blocksCount.toDouble()
            text = "${ChatColor.WHITE}Поставлено блоков: ${ChatColor.WHITE}$blocksPlaced ${ChatColor.WHITE}из ${ChatColor.AQUA}${structure.blocksCount}"
        }

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