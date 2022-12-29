package me.slavita.construction.project

import me.slavita.construction.player.City
import me.slavita.construction.player.sound.Music.playSound
import me.slavita.construction.player.sound.MusicSound
import me.slavita.construction.reward.Reward
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.structure.tools.StructureState
import org.bukkit.ChatColor

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
        owner.cities.forEach {
            projectsCount += it.projects.size
        }
        id = owner.data.statistics.totalProjects + projectsCount
    }

    fun start() {
        structure.startBuilding(this)
        owner.player.playSound(MusicSound.SUCCESS3)
    }

    fun onEnter() {
        when (structure.state) {
            StructureState.BUILDING       -> structure.showVisual()
            StructureState.FINISHED       -> {
                structure.claimed()
                rewards.forEach { reward ->
                    reward.getReward(owner)
                }
                structure.cityStructure!!.state = CityStructureState.FUNCTIONING
                structure.cityStructure!!.startIncome()
                city.finishProject(this@Project)
                owner.player.playSound(MusicSound.UI_CLICK)
                owner.data.statistics.totalProjects++
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

    override fun toString() = """
        Информация про проект:
          ${ChatColor.AQUA}ID: ${id}
          ${ChatColor.AQUA}Награды:
          ${rewards.joinToString("\n  ") { it.toString() }}
    """.trimIndent()
}