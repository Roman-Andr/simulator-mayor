package me.slavita.construction.market.showcase

import me.slavita.construction.app
import me.slavita.construction.world.ItemProperties

object Showcases {
    val showcases = hashSetOf<ShowcaseProperties>()

    init {
        val materials = app.allBlocks
        app.mainWorld.map.getLabels("showcase").forEach { label ->
            showcases.add(ShowcaseProperties(label.tagInt, hashSetOf<Pair<ItemProperties, Long>>().apply {
                materials.forEach { material ->
                    this.add(Pair(material, 100L))
                }
            }))
        }
    }
}