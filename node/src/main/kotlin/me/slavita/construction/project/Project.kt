package me.slavita.construction.project

import me.slavita.construction.player.City
import me.slavita.construction.player.User
import me.slavita.construction.reward.Reward
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound

class Project(
    val city: City,
    var id: Int,
    val structure: BuildingStructure,
    val rewards: List<Reward>,
) {
    val owner = city.owner
    val timeLast: Int
        get() = 0

    init {
        var projectsCount = 0
        owner.cites.forEach {
            projectsCount += it.projects.size
        }
        id = owner.stats.totalProjects + projectsCount
    }

    fun start() {
        structure.startBuilding(this)
    }

    fun onEnter() {
        when (structure.state) {
            StructureState.BUILDING       -> structure.showVisual()
            StructureState.FINISHED       -> {
                structure.claimed()
                rewards.forEach {
                    it.getReward(owner)
                }
                structure.cityStructure!!.state = CityStructureState.FUNCTIONING
                structure.cityStructure!!.startIncome()
                city.finishProject(this@Project)
                owner.player.playSound(MusicSound.UI_CLICK)
                owner.stats.totalProjects++
            }

            StructureState.REWARD_CLAIMED -> {}
            StructureState.NOT_STARTED    -> {}
        }
    }

    fun onLeave() {
        when (structure.state) {
            StructureState.BUILDING -> structure.hideVisual()
            else                    -> {}
        }
    }
}