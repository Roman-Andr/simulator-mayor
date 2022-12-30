package me.slavita.construction.structure.tools

import me.func.mod.Anime
import me.func.mod.reactive.ReactivePlace
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.element.Banner
import me.func.protocol.world.marker.Marker
import me.func.protocol.world.marker.MarkerSign
import me.slavita.construction.banner.BannerUtil
import me.slavita.construction.structure.CityStructure
import me.slavita.construction.utils.blocksDeposit
import me.slavita.construction.utils.killboard
import me.slavita.construction.world.ItemProperties

class CityStructureVisual(val structure: CityStructure) {
    private var redBanner: Banner? = null
    private var repairGlow: ReactivePlace? = null
    private var marker: Marker? = null

    init {
        val center = structure.playerCell.box.center
        redBanner = BannerUtil.createFloorBanner(
            center.clone().apply {
                y = structure.box.min.y - 22.49
                z = structure.box.min.z
            }, GlowColor.RED
        )
        marker = Marker(center.x, center.y, center.z, 80.0, MarkerSign.ARROW_DOWN)
        repairGlow = ReactivePlace.builder()
            .rgb(GlowColor.GREEN_LIGHT)
            .radius(2.0)
            .location(structure.playerCell.box.min.apply { y -= 2.5 })
            .onEntire { player ->
                player.killboard("Почини")
                blocksDeposit(player, structure.targetBlocks, structure.repairBlocks)
                structure.repairBlocks.forEach {
                    structure.targetBlocks[it.key] = structure.targetBlocks[it.key]!! - it.value
                    if (structure.targetBlocks[it.key]!! <= 0) structure.targetBlocks.remove(it.key)
                }
                if (structure.targetBlocks.isEmpty()) structure.repair()
            }
            .build()
    }

    fun update() {
        println("updating visual")
        when (structure.state) {
            CityStructureState.NOT_READY   -> {
                Banners.hide(structure.owner, redBanner!!)
                Anime.removeMarker(structure.owner, marker!!)
            }

            CityStructureState.FUNCTIONING -> {
                Banners.hide(structure.owner, redBanner!!)
                Anime.removeMarker(structure.owner, marker!!)
                repairGlow!!.delete(setOf(structure.owner))
            }

            CityStructureState.BROKEN      -> {
                println("showing")
                Banners.show(structure.owner, redBanner!!)
                Anime.marker(structure.owner, marker!!)
                repairGlow!!.send(structure.owner)
                structure.targetBlocks = structure.structure.blocks.clone() as HashMap<ItemProperties, Int>
            }
        }
    }
}