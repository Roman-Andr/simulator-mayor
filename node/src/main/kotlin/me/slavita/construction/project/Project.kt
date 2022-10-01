package me.slavita.construction.project

import me.slavita.construction.player.User
import me.slavita.construction.structure.BuildingStructure
import net.minecraft.server.v1_12_R1.BlockPosition

class Project(
    val owner: User,
    var id: Int,
    val structure: BuildingStructure,
    val stats: ProjectStatistics,
    val place: BlockPosition
) {
    init {
        owner.activeProjects.add(this)
        id = owner.stats.totalProjects
    }

    fun start() {
        structure.startBuilding()
    }
}