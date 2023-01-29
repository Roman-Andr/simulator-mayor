package me.slavita.construction.project

import me.slavita.construction.player.User
import me.slavita.construction.reward.ExperienceReward
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.reward.ReputationReward
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.structure.instance.Structure

object ProjectGenerator {
    fun generateClient(owner: User, structure: Structure, playerCell: PlayerCell): Project {
        return Project(
            owner.currentCity,
            owner.data.totalProjects,
            hashSetOf(
                MoneyReward(100),
                ExperienceReward(100),
                ReputationReward(100),
            )
        ).apply {
            this.structure = ClientStructure(structure, playerCell)
            initialize()
        }
    }

    fun generateWorker(owner: User, structure: Structure, playerCell: PlayerCell): Project {
        return Project(
            owner.currentCity,
            owner.data.totalProjects,
            hashSetOf(
                MoneyReward(300),
                ExperienceReward(200),
                ReputationReward(100),
            )
        ).apply {
            this.structure = WorkerStructure(structure, playerCell)
            initialize()
        }
    }

    fun generateFreelance(owner: User, structure: Structure): FreelanceProject {
        return FreelanceProject(
            owner.currentCity,
            owner.data.totalProjects,
            ClientStructure(
                structure,
                owner.freelanceCell
            ),
            hashSetOf(
                MoneyReward(100),
                ExperienceReward(100),
                ReputationReward(100),
            )
        )
    }
}
