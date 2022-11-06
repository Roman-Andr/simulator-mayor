package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.structure.CityStructure
import me.slavita.construction.structure.Cell
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import org.bukkit.Bukkit

class City(val owner: User) {
    val projects = hashSetOf<Project>()
    val cityStructures = hashSetOf<CityStructure>()
    val cells = arrayListOf<Cell>()

    init {
        app.mainWorld.map.getLabels("place").forEachIndexed { id, label ->
            cells.add(Cell(owner, id, label, false))
        }
        Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
            if (cityStructures.size == 0) return@scheduleSyncRepeatingTask
            cityStructures.shuffled().chunked(5)[0].forEach {
                if(it.state == CityStructureState.BROKEN || it.state == CityStructureState.NOT_READY) return@scheduleSyncRepeatingTask
                owner.income -= it.structure.income
                it.state = CityStructureState.BROKEN
                owner.player.killboard("Здание #${it.cell.id} сломалось")
                it.visual.update()
            }
//        }, 0L, 10 * 60 * 20L)
        }, 0L, 10 * 20L)
    }

    fun addStructure(cityStructure: CityStructure): CityStructure {
        cityStructures.add(cityStructure)
        return cityStructure
    }

    fun addProject(project: Project) {
        projects.add(project)
    }

    fun finishProject(project: Project) {
        projects.remove(project)
    }
}