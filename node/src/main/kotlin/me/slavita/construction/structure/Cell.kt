package me.slavita.construction.structure

import me.func.unit.Building
import me.func.world.Label
import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.world.Box
import org.bukkit.Location
import org.bukkit.block.BlockFace
import java.util.*

open class Cell(val owner: User, val id: Int, val label: Label, var busy: Boolean) {

    val face: BlockFace = try {
        BlockFace.valueOf(label.tag.uppercase())
    } catch (exception: Exception) {
        println("Illegal label: $label")
        BlockFace.WEST
    }

    val box = Box(label.clone().add(1.0, -1.0, 1.0), label.clone().add(24.0, 47.0, 24.0))

    fun setBusy() {
        busy = true
    }
}