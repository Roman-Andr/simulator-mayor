package me.slavita.construction.structure.tools

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
import me.slavita.construction.banner.BannerInfo
import me.slavita.construction.banner.BannerUtil
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.utils.extensions.BlocksExtensions.unaryMinus
import me.slavita.construction.utils.extensions.BlocksExtensions.withOffset
import org.bukkit.ChatColor
import org.bukkit.block.BlockFace

class StructureVisual(
    val structure: BuildingStructure
) {
    private var floorBanner: Banner? = null
    private var infoBanner: Banner? = null
    private var progressWorld: ReactiveProgress? = null
    private var marker: Marker? = null
    private val owner = structure.owner
    private val progressBar = StructureProgressBar(owner, structure.structure.blocksCount)

    fun start() {
        val center = structure.structure.box.center.withOffset(-structure.structure.box.min).withOffset(structure.allocation)
        floorBanner = BannerUtil.create(
            BannerInfo(
            center.clone().apply { z = structure.allocation.z }.apply { y = structure.allocation.y - 22.49 },
            BlockFace.UP,
            listOf(),
            16*23,
            16*23,
            GlowColor.BLUE,
            0.24,
            MotionType.CONSTANT,
            -90.0f
        )
        )
        infoBanner = BannerUtil.create(
            BannerInfo(
            center.clone().apply { z = structure.allocation.z }.apply { y = structure.allocation.y },
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
        )
        )
        progressWorld = ReactiveProgress.builder()
            .position(Position.BOTTOM)
            .offsetX(structure.allocation.x)
            .offsetY(structure.allocation.y + 5.0)
            .offsetZ(structure.allocation.z)
            .hideOnTab(false)
            .color(GlowColor.GREEN)
            .build()
        marker = Marker(center.x, center.y, center.z, 80.0, MarkerSign.ARROW_DOWN)
        update()
        hide()
    }

    fun update() {
        progressBar.update(structure.blocksPlaced)
        progressWorld!!.apply {
            progress = structure.blocksPlaced.toDouble() / structure.structure.blocksCount.toDouble()
            text = "${ChatColor.WHITE}Поставлено блоков: ${ChatColor.WHITE}${structure.blocksPlaced} ${ChatColor.WHITE}из ${ChatColor.AQUA}${structure.structure.blocksCount}"
        }
    }

    fun show() {
        println("show")
        Banners.hide(owner, floorBanner!!)
        Banners.hide(owner, infoBanner!!)
        Anime.removeMarker(owner, marker!!)
        progressWorld!!.send(owner)
        progressBar.show()
    }

    fun hide() {
        println("hide")
        Banners.show(owner, floorBanner!!)
        Banners.show(owner, infoBanner!!)
        Anime.marker(owner, marker!!)
        progressWorld!!.delete(setOf(owner))
        progressBar.hide()
    }

    fun delete() {
        Banners.hide(owner, floorBanner!!)
        Banners.hide(owner, infoBanner!!)
        Anime.removeMarker(owner, marker!!)
        progressWorld!!.delete(setOf(owner))
        progressBar.hide()
    }
}