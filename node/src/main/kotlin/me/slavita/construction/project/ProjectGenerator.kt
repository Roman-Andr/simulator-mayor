package me.slavita.construction.project

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.instance.Structures
import org.bukkit.Location

object ProjectGenerator {
    fun generateClient(owner: User, structure: Structure, location: Location): Project {
        return Project(
            owner,
            owner.stats.totalProjects + owner.activeProjects.size,
            ClientStructure(
                app.mainWorld,
                structure,
                owner,
                location
            ),
            listOf(
                MoneyReward(10)
            )
        )
    }

    fun generateWorker(owner: User, structure: Structure, location: Location): Project {
        return Project(
            owner,
            owner.stats.totalProjects + owner.activeProjects.size,
            WorkerStructure(
                app.mainWorld,
                structure,
                owner,
                location
            ),
            listOf(
                MoneyReward(10)
            )
        )
    }
}