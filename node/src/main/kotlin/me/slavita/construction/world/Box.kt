package me.slavita.construction.world

import org.bukkit.Location

class Box(val min: Location, val max: Location) {
    fun contains(location: Location): Boolean {
        return min.getX() <= location.getX() && max.getX() >= location.getX() &&
                min.getY() <= location.getY() && max.getY() >= location.getY() &&
                min.getZ() <= location.getZ() && max.getZ() >= location.getZ()
    }
}