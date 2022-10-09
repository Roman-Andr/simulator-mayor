package me.slavita.construction.project

import me.slavita.construction.player.User
import me.slavita.construction.reward.Reward
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.structure.tools.StructureState

class Project(
    val owner: User,
    var id: Int,
    val structure: BuildingStructure,
    val rewards: List<Reward>
) {
    init {
        id = owner.stats.totalProjects
    }

    fun start() {
        structure.startBuilding(this)
    }

    fun onEnter() {
        when (structure.state) {
            StructureState.BUILDING -> structure.showVisual()
            StructureState.FINISHED -> {
                structure.claimed()
                rewards.forEach {
                    it.getReward(owner)
                }
                owner.activeProjects.remove(this@Project)
                owner.stats.totalProjects++
            }
            StructureState.REWARD_CLAIMED -> {}
            StructureState.NOT_STARTED -> {}
        }
    }

    fun onLeave() {
        when (structure.state) {
            StructureState.BUILDING -> structure.hideVisual()
            else -> {}
        }
    }
}