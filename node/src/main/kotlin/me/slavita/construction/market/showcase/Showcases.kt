package me.slavita.construction.market.showcase

import me.func.atlas.Atlas
import me.slavita.construction.app
import me.slavita.construction.utils.extensions.LoggerUtils.log
import me.slavita.construction.world.ItemProperties

object Showcases {
    val showcases = hashSetOf<ShowcaseProperties>()

    init {
        val materials = app.allBlocks
        Atlas.find("showcases").getMapList("showcases").forEach { values ->
            showcases.add(ShowcaseProperties(values["id"] as Int, values["title"] as String, hashSetOf<Pair<ItemProperties, Long>>().apply {
                materials.forEach { material ->
                    this.add(Pair(material, 100L))
                }
            }))
        }
    }
}