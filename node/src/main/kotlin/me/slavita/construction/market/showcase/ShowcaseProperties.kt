package me.slavita.construction.market.showcase

import org.bukkit.Material

class ShowcaseProperties(val boxName: String, val name: String, vararg materials: Material) {
    val materials = materials.toList()
}