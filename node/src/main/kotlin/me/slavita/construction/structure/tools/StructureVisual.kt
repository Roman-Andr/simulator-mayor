package me.slavita.construction.structure.tools

import me.func.mod.Anime
import me.func.mod.reactive.ReactivePlace
import me.func.mod.reactive.ReactiveProgress
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.Tricolor
import me.func.protocol.data.element.Banner
import me.func.protocol.math.Position
import me.func.protocol.world.marker.Marker
import me.func.protocol.world.marker.MarkerSign
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.utils.*
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE

class StructureVisual(val structure: BuildingStructure) {

    private val infoGlow = ReactivePlace.builder()
        .rgb(GlowColor.BLUE)
        .location(structure.box.bottomCenter.clone().apply { y -= 2.0 })
        .radius(11.0)
        .angles(120)
        .build()
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
        infoGlow.send(owner.player)

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
        infoGlow.delete(setOf(owner.player))
        Anime.removeMarker(owner.player, marker!!)
        progressWorld!!.send(owner.player)
        progressBar.show()
        update()
    }

    fun hide() {
        infoGlow.send(owner.player)
        Anime.marker(owner.player, marker!!)
        progressWorld!!.delete(setOf(owner.player))
        progressBar.hide()
    }

    fun delete() {
        infoGlow.delete(setOf(owner.player))
        Banners.hide(owner.player, infoBanners!!)
        Anime.removeMarker(owner.player, marker!!)
        progressWorld!!.delete(setOf(owner.player))
        progressBar.hide()
    }

    fun finishShow() {
        infoGlow.rgb = GlowColor.GREEN
        infoGlow.send(owner.player)
    }

    fun hideFinish() {
        infoGlow.rgb = GlowColor.GREEN
        infoGlow.delete(setOf(owner.player))
    }
}
