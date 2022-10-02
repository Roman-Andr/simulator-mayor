package me.slavita.construction.project

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.structure.BuildingStructure

class Project(
    val owner: User,
    var id: Int,
    val structure: BuildingStructure,
    val stats: ProjectStatistics
) {
    init {
        id = owner.stats.totalProjects
    }

    fun start() {
        structure.startBuilding(this)
    }
}