package me.slavita.construction.market.showcase

import me.slavita.construction.app
import me.slavita.construction.world.ItemProperties

object Showcases {
    val showcases = hashSetOf<ShowcaseProperties>()

    init {
        val materials = app.allBlocks
        (1..3).forEach { index ->
            showcases.add(ShowcaseProperties(index, index.toString(), hashSetOf<Pair<ItemProperties, Long>>().apply {
                materials.forEach { material ->
                    this.add(Pair(material, 100L))
                }
            }))
        }
    }
}