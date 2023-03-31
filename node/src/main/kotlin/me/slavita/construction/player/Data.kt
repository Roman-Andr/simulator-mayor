package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.region.*
import me.slavita.construction.reward.Reward
import me.slavita.construction.ui.achievements.AchievementData
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.utils.mapS
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.SlotItem

class Data(@Transient var user: User) {

    var money: Long = 0
    var totalProjects: Int = 0
    var boughtBlocks: Int = 0
    var freelanceProjectsCount: Int = 0
    var reputation: Long = 0
        set(value) {
            field = maxOf(0, value)
        }
    var level: Int = 1
    var experience: Long = 0
    var speed: Float = 0.2F
    var trainStep: Int = 0
    var nextDay: Int = 0
    var nextTakeDailyReward: Long = 0
    var dailyRewards: List<Reward> = listOf()
    var totalBoosters: Long = 0
    var lastIncome: Long = 0

    val inventory: MutableList<SlotItem> = mutableListOf()
    val workers: HashSet<Worker> = hashSetOf()
    val settings: SettingsData = SettingsData()
    var tag: Tags = Tags.NONE
    val ownTags: HashSet<Tags> = hashSetOf(Tags.NONE)
    val abilities: HashSet<Abilities> = hashSetOf()
    //val hall: Hall = Hall()
    var hasFreelance: Boolean = false
    var achievements: HashSet<AchievementData> = AchievementType.values().map { AchievementData(it) }.toHashSet()
    val regions: HashSet<Region> = Regions.regions.map { Region(user, it.value) }.toHashSet()
    val cells: HashSet<Cell> = hashSetOf<Cell>().apply {
        app.mainWorld.cells.forEach { cell ->
            add(ClassicCell(user, cell, regions.first { it.options.box.contains(cell.label) }))
        }
        add(FreelanceCell(user, app.mainWorld.freelanceCell))
    }

    //todo: rewrite
    fun addMoney(value: Long) {
        money += (value)
        //user.updateAchievement(AchievementType.MONEY)
    }

    fun addExp(exp: Long) {
        experience += exp
    }

    fun addProjects(value: Int) {
        totalProjects += value
        //user.updateAchievement(AchievementType.PROJECTS)
    }

    fun addBlocks(value: Int) {
        boughtBlocks += value
        //user.updateAchievement(AchievementType.BOUGHT_BLOCKS)
    }

    fun addFreelanceProjects(value: Int) {
        freelanceProjectsCount += value
        //user.updateAchievement(AchievementType.FREELANCE)
    }
}
