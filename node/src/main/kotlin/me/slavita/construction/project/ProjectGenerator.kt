package me.slavita.construction.project

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.structure.instance.Structures

object ProjectGenerator {
    fun generateClient(owner: User): Project {
        val location = owner.getEmptyPlace()!!
        return Project(
            owner,
            owner.stats.totalProjects+owner.activeProjects.size,
            ClientStructure(
                app.mainWorld,
                Structures.structureGroups.random().structures.random(),
                owner,
                location
            ),
            listOf(
                MoneyReward(10)
            )
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
                owner,
                location
            ),
            listOf(
                MoneyReward(10)
            )
        )
    }
}