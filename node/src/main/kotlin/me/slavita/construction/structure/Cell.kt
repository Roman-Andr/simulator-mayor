package me.slavita.construction.structure

import me.func.world.Label
import me.slavita.construction.player.City
import me.slavita.construction.world.Box
import org.bukkit.block.BlockFace

open class Cell(val city: City, val id: Int, label: Label, var busy: Boolean) {

    val face: BlockFace = try {
        BlockFace.valueOf(label.tag.uppercase())
    } catch (exception: Exception) {
        println("Illegal label: $label")
        BlockFace.WEST
    }

    val owner = city.owner
    val box = Box(label.clone().add(1.0, -1.0, 1.0), label.clone().add(24.0, 47.0, 24.0))

    fun setBusy() {
        busy = true
    }
}