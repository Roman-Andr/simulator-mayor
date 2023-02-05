package me.slavita.construction.player

import me.func.atlas.Atlas
import me.slavita.construction.city.City
import me.slavita.construction.city.CityHall
import me.slavita.construction.city.storage.BlocksStorage
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.ui.achievements.AchievementData
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.SlotItem

class Data(@Transient var user: User) {

    var money: Long = 0
        set(value) {
            field = value
            user.updateAchievement(AchievementType.MONEY)
        }
    var level: Int = 1
    var experience: Long = 0
    var reputation: Long = 0
    var totalProjects: Int = 0
        set(value) {
            field = value
            user.updateAchievement(AchievementType.PROJECTS)
        }
    var speed: Float = 0.2F
    var trainStep: Int = 0
    var nextDay: Int = 0
    var nextTakeDailyReward: Long = 0
    var totalBoosters: Long = 0
    var lastIncome: Long = 0
    var freelanceProjectsCount: Int = 0
        set(value) {
            field = value
            user.updateAchievement(AchievementType.FREELANCE)
        }
    var boughtBlocks: Long = 0
        set(value) {
            field = value
            user.updateAchievement(AchievementType.BOUGHT_BLOCKS)
        }
    var rebirths: Long = 0

    val inventory: HashSet<SlotItem> = hashSetOf()
    val workers: HashSet<Worker> = hashSetOf()
    val settings: SettingsData = SettingsData()
    var tag: Tags = Tags.NONE
    val ownTags: HashSet<Tags> = hashSetOf(Tags.NONE)
    val abilities: HashSet<Abilities> = hashSetOf()
    val hall: CityHall = CityHall()
    val blocksStorage = BlocksStorage(user)
    var hasFreelance: Boolean = false
    var achievements: HashSet<AchievementData> = AchievementType.values().map { AchievementData(0, it) }.toHashSet()
    var lastCityId: String = "1"
    val cities: HashSet<City> = Atlas.find("locations").getMapList("locations").map { values ->
        City(
            user,
            values["id"] as String,
            values["title"] as String,
            (values["price"] as Int).toLong(),
            values["id"] as String == "1"
        )
    }.toHashSet()
}
