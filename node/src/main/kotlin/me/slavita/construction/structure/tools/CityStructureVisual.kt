package me.slavita.construction.structure.tools

import me.func.mod.Anime
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.element.Banner
import me.func.protocol.world.marker.Marker
import me.func.protocol.world.marker.MarkerSign
import me.slavita.construction.banner.BannerUtil
import me.slavita.construction.structure.CityStructure
import org.bukkit.block.BlockFace

class CityStructureVisual(val structure: CityStructure) {
    var redBanner: Banner? = null
    var marker: Marker? = null

    init {
        val center = structure.box.center
        redBanner = BannerUtil.createFloorBanner(
            center.clone().apply {
                y = structure.box.min.y - 22.49
                z = structure.box.min.z
            }, GlowColor.RED
        )
        marker = Marker(center.x, center.y, center.z, 80.0, MarkerSign.ARROW_DOWN)
    }

    fun update() {
        when (structure.state) {
            CityStructureState.NOT_READY -> {
                Banners.hide(structure.owner, redBanner!!)
                Anime.removeMarker(structure.owner, marker!!)
            }

            CityStructureState.FUNCTIONING -> {
                Banners.hide(structure.owner, redBanner!!)
                Anime.removeMarker(structure.owner, marker!!)
            }

            CityStructureState.BROKEN -> {
                Banners.show(structure.owner, redBanner!!)
                Anime.marker(structure.owner, marker!!)
            }
        }
    }
}