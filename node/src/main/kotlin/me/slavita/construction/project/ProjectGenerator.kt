package me.slavita.construction.project

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.structure.Cell
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.structure.instance.Structure
import org.bukkit.Location

object ProjectGenerator {
    fun generateClient(owner: User, structure: Structure, cell: Cell): Project {
        return Project(
            owner,
            owner.stats.totalProjects,
            ClientStructure(
                app.mainWorld,
                structure,
                owner,
                cell
            ),
            listOf(
                MoneyReward(10)
            )
        )
    }

    fun generateWorker(owner: User, structure: Structure, cell: Cell): Project {
        return Project(
            owner,
            owner.stats.totalProjects,
            WorkerStructure(
                app.mainWorld,
                structure,
                owner,
                cell
            ),
            listOf(
                MoneyReward(10)
            )
        )
    }
}