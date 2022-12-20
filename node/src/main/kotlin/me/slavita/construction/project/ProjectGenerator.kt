package me.slavita.construction.project

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.reward.ExperienceReward
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.reward.ReputationReward
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.structure.instance.Structure

object ProjectGenerator {
    fun generateClient(owner: User, structure: Structure, playerCell: PlayerCell): Project {
        return Project(
            owner.currentCity,
            owner.data.statistics.totalProjects,
            ClientStructure(
                app.mainWorld,
                structure,
                owner,
                playerCell
            ),
            listOf(
                MoneyReward(100),
                ExperienceReward(100),
                ReputationReward(100),
            )
        )
    }

    fun generateWorker(owner: User, structure: Structure, playerCell: PlayerCell): Project {
        return Project(
            owner.currentCity,
            owner.data.statistics.totalProjects,
            WorkerStructure(
                app.mainWorld,
                structure,
                owner,
                playerCell
            ),
            listOf(
                MoneyReward(300),
                ExperienceReward(200),
                ReputationReward(100),
            )
        )
    }
}
