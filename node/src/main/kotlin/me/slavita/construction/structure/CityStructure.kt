package me.slavita.construction.structure

import me.func.protocol.data.color.GlowColor
import me.func.unit.Building
import me.func.world.Box
import me.slavita.construction.app
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.structure.tools.CityStructureVisual
import me.slavita.construction.utils.Alert
import me.slavita.construction.utils.Alert.button
import me.slavita.construction.utils.user
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player
import java.util.*

class CityStructure(val owner: Player, val structure: Structure, val cell: PlayerCell) {

    val box = Box(app.structureMap, structure.box.min, structure.box.max, "", "")
    val building = Building(UUID.randomUUID(), "", "", 0.0, 0.0, 0.0, box)
    var state = CityStructureState.NOT_READY
    val visual = CityStructureVisual(this)
    var repairBlocks: HashMap<ItemProperties, Int> = hashMapOf()
    var targetBlocks: HashMap<ItemProperties, Int> = HashMap(structure.blocks)

    init {
        building.allocate(cell.box.min.clone().add(11.0, 0.0, 11.0))
        building.show(owner)
        visual.update()
    }

    fun repair() {
        startIncome()
        state = CityStructureState.FUNCTIONING
        repairBlocks = hashMapOf()
        Alert.send(
            owner.player,
            """
                    ${WHITE}Отремонтировано
                    ${GRAY}Номер: ${GRAY}${cell.id}
                    ${AQUA}Название: ${GOLD}${structure.name}
                    ${GOLD}Локация: ${GREEN}${cell.city.title}
                """.trimIndent(),
            5000,
            GlowColor.BLUE,
            GlowColor.BLUE_MIDDLE,
            null,
            button("Понятно", "/ok", GlowColor.GREEN),
        )
        visual.update()
    }

    fun startIncome() {
        owner.user.income += structure.income
    }
}
