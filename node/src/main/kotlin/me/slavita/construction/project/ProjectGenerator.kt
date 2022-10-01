package me.slavita.construction.project

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.utils.extensions.BlocksExtensions.toLocation

object ProjectGenerator {
    fun generateClient(owner: User): Project {
        val location = owner.getEmptyPlace()!!
        return Project(
            owner,
            owner.stats.totalProjects+owner.activeProjects.size,
            ClientStructure(
                app.mainWorld,
                Structures.structureGroups.random().structures.random(),
                owner.player,
                location.toLocation(app.mainWorld.map.world)
            ),
            ProjectStatistics(1000),
            location
        )
    }

    fun generateWorker(owner: User): Project {
        val location = owner.getEmptyPlace()!!
        return Project(
            owner,
            owner.stats.totalProjects+owner.activeProjects.size,
            WorkerStructure(
                app.mainWorld,
                Structures.structureGroups.random().structures.random(),
                owner.player,
                location.toLocation(app.mainWorld.map.world),
                hashSetOf()
            ),
            ProjectStatistics(1000),
            location
        )
    }
}