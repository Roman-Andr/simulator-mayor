package me.slavita.construction.world

import dev.implario.games5e.sdk.cristalix.WorldMeta
import me.func.unit.Building
import me.slavita.construction.app
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

class Structure(val owner : UUID, val structureType: StructureType, val location: Location) {
    val building = Building(owner, "structure", structureType.id, app.structureMap)

    fun show(player: Player) {
        if (building.allocation == null) building.allocate(location)
        building.show(player)
    }

    fun hide(player: Player) {
        building.hide(player)
        building.deallocate()
    }
}