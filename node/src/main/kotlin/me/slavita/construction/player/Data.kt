package me.slavita.construction.player

import me.slavita.construction.city.City
import me.slavita.construction.city.CityHall
import me.slavita.construction.city.CitySamples
import me.slavita.construction.city.storage.BlocksStorage
import me.slavita.construction.dontate.Abilities
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
    val hall: CityHall = CityHall()
    val blocksStorage = BlocksStorage(user)
    var hasFreelance: Boolean = false
    var achievements: HashSet<AchievementData> = AchievementType.values().map { AchievementData(it) }.toHashSet()
    var lastCityId: String = "1"
    val cities: HashSet<City> = CitySamples.values().mapS { sample ->
        City(
            user,
            sample.id.toString(),
            sample.title,
            sample.price,
            sample.id == 1
        )
    }

    fun addMoney(value: Long) {
        money += (value)
        user.updateAchievement(AchievementType.MONEY)
    }

    fun addExp(exp: Long) {
        experience += exp
    }

    fun addProjects(value: Int) {
        totalProjects += value
        user.updateAchievement(AchievementType.PROJECTS)
    }

    fun addBlocks(value: Int) {
        boughtBlocks += value
        user.updateAchievement(AchievementType.BOUGHT_BLOCKS)
    }

    fun addFreelanceProjects(value: Int) {
        freelanceProjectsCount += value
        user.updateAchievement(AchievementType.FREELANCE)
    }
}
