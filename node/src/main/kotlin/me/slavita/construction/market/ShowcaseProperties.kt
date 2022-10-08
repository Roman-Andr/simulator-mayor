package me.slavita.construction.market

import me.slavita.construction.app
import me.slavita.construction.structure.instance.Structures
import org.bukkit.Material

class ShowcaseProperties(val boxName: String, val name: String, vararg materials: Material) {
    val materials = materials.toList()
}