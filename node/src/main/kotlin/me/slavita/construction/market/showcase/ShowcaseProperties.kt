package me.slavita.construction.market.showcase

import me.slavita.construction.world.ItemProperties

class ShowcaseProperties(val id: Int, val boxName: String, var elements: HashSet<Pair<ItemProperties, Long>>) {
	val name
		get() = "Магазин №$id"
}