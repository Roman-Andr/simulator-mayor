package me.slavita.construction.project

import me.slavita.construction.app
import me.slavita.construction.player.City
import me.slavita.construction.player.sound.Music.playSound
import me.slavita.construction.player.sound.MusicSound
import me.slavita.construction.reward.Reward
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.BlocksExtensions.unaryMinus
import org.bukkit.ChatColor
import org.bukkit.Material

open class Project(
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

    open fun onEnter() {
        when (structure.state) {
            StructureState.BUILDING       -> structure.showVisual()
            StructureState.FINISHED       -> {
                finish()
                structure.cityStructure!!.state = CityStructureState.FUNCTIONING
                structure.cityStructure!!.startIncome()
                city.finishProject(this@Project)
            }

            else -> {}
        }
    }

    fun finish() {
        structure.claimed()
        rewards.forEach { reward ->
            reward.getReward(owner)
        }
        owner.player.playSound(MusicSound.UI_CLICK)
        owner.data.statistics.totalProjects++
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
