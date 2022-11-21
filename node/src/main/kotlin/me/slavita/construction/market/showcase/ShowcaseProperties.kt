package me.slavita.construction.market.showcase

import me.func.atlas.Atlas
import me.slavita.construction.world.ItemProperties

class ShowcaseProperties(val id: Int, var elements: HashSet<Pair<ItemProperties, Long>>) {
    val name: String
        get() = Atlas.find("showcases").getString("showcases.$id.title")
}