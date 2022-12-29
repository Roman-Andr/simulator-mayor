package me.slavita.construction.showcase

import me.slavita.construction.world.ItemProperties

class ShowcaseProperties(val id: Int, val name: String, var elements: HashSet<Pair<ItemProperties, Long>>)