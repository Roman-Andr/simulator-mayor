package me.slavita.construction.structure.tools

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveProgress
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.Tricolor
import me.func.protocol.data.element.Banner
import me.func.protocol.math.Position
import me.func.protocol.world.marker.Marker
import me.func.protocol.world.marker.MarkerSign
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.utils.BannerInfo
import me.slavita.construction.utils.borderBuilder
import me.slavita.construction.utils.createDual
import me.slavita.construction.utils.getFaceCenter
import me.slavita.construction.utils.hide
import me.slavita.construction.utils.show
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE

class StructureVisual(val structure: BuildingStructure) {
    private val border = borderBuilder(structure.box.bottomCenter, GlowColor.BLUE).alpha(60).build()
    private var infoBanners: Pair<Banner, Banner>? = null
    private var progressWorld: ReactiveProgress? = null
    private var marker: Marker? = null
    private val owner = structure.owner
    private val progressBar = StructureProgressBar(owner.player, structure.structure.blocksCount)

    private val bannerLocation = getFaceCenter(structure.cell)

    fun start() {
        val center = structure.box.center

        infoBanners = createDual(
            BannerInfo(
                bannerLocation,
                structure.cell.face,
                structure.getBannerInfo(),
                102,
                80,
                Tricolor(0, 0, 0),
                0.65
            )
        )

        progressWorld = ReactiveProgress.builder()
            .position(Position.BOTTOM)
            .offsetX(bannerLocation.x)
            .offsetY(structure.allocation.y + 6.0)
            .offsetZ(bannerLocation.z)
            .hideOnTab(false)
            .color(GlowColor.GREEN)
            .scale(2.5)
            .build()

        marker = Marker(center.x, center.y, center.z, 80.0, MarkerSign.ARROW_DOWN)
        Banners.show(owner.player, infoBanners!!)
        border.send(owner.player)

        update()
    }

    fun update() {
        progressBar.update(structure.blocksPlaced)
        progressWorld!!.apply {
            progress = structure.blocksPlaced.toDouble() / structure.structure.blocksCount.toDouble()
            text =
                "${WHITE}Поставлено блоков: ${WHITE}${structure.blocksPlaced} ${WHITE}из ${AQUA}${structure.structure.blocksCount}"
        }
        infoBanners!!.toList().forEach { it ->
            Banners.content(owner.player, it, structure.getBannerInfo().joinToString("\n") { it.first })
        }
    }

    fun show() {
        Anime.removeMarker(owner.player, marker!!)
        progressWorld!!.send(owner.player)
        progressBar.show()
        update()
    }

    fun hide() {
        Anime.marker(owner.player, marker!!)
        progressWorld!!.delete(setOf(owner.player))
        progressBar.hide()
    }

    fun delete() {
        border.delete(owner.player)
        Banners.hide(owner.player, infoBanners!!)
        Anime.removeMarker(owner.player, marker!!)
        progressWorld!!.delete(setOf(owner.player))
        progressBar.hide()
    }

    fun finishShow() {
        border.color = GlowColor.GREEN
        border.update(owner.player)
    }

    fun hideFinish() {
        border.delete(owner.player)
    }
}
