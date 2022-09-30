package me.slavita.construction.project

import me.slavita.construction.player.User
import me.slavita.construction.structure.BuildingStructure

class Project(
    val owner: User,
    val structure: BuildingStructure,
    val stats: ProjectStatistics
) {
    init {
        owner.activeProjects.add(this)
    }

    fun start() {
        structure.startBuilding()
    }
}