package me.slavita.construction.structure

import me.slavita.construction.world.Box
import org.bukkit.Location

open class Cell(val id: Int, allocation: Location, var busy: Boolean) {
    val box = Box(allocation, allocation.clone().add(22.0, 47.0, 22.0))
}