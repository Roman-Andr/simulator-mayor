package me.slavita.construction.region

import me.func.mod.Anime
import me.func.protocol.world.marker.Marker
import me.func.protocol.world.marker.MarkerSign
import me.slavita.construction.utils.*
import org.bukkit.ChatColor

abstract class Structure(val options: StructureOptions, open val cell: Cell) {

    val user = cell.user
    val box = cell.box
    val allocation = cell.allocation
    val blocksCount = options.blocksCount

    var marker = box.center.run { Marker(x, y, z, 80.0, MarkerSign.ARROW_DOWN) }
    var infoBanner = createStructureBanner(cell)

    open fun enter() {
        Anime.removeMarker(user.player, marker)
        infoBanner.show()
    }

    open fun leave() {
        Anime.marker(user.player, marker)
    }

    open fun remove() {
        Anime.removeMarker(user.player, marker)
        leave()
    }

    abstract fun allocate()
}
