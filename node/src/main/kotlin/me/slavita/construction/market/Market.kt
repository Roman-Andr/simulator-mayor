package me.slavita.construction.market

import me.func.world.Box
import me.slavita.construction.app
import me.slavita.construction.structure.instance.Structures
import org.bukkit.Material

class Market {
    init {
        Showcases.showcases.forEach {
            Showcase(it)
        }
    }
}