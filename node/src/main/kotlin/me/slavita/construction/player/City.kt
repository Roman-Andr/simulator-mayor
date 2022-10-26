package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.structure.CityStructure
import me.slavita.construction.structure.Cell

class City(val owner: User) {
    val projects = hashSetOf<Project>()
    val cityStructures = hashSetOf<CityStructure>()
    val cells = arrayListOf<Cell>()

    init {
        app.mainWorld.map.getLabels("place").forEachIndexed { id, label ->
            cells.add(Cell(owner, id, label, false))
        }
    }

    fun addProject(project: Project) {
        projects.add(project)
    }

    fun finishProject(project: Project) {
        projects.remove(project)
    }
}