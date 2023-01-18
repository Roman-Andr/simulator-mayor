package me.slavita.construction.player

import me.slavita.construction.dontate.Abilities
import me.slavita.construction.showcase.Showcase
import me.slavita.construction.ui.achievements.AchievementData
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.worker.Worker

data class Data(
    var statistics: Statistics = Statistics(),
    var workers: HashSet<Worker> = hashSetOf(),
    var settings: SettingsData = SettingsData(),
    var tag: Tags = Tags.NONE,
    var ownTags: HashSet<Tags> = hashSetOf(Tags.NONE),
    val abilities: HashSet<Abilities> = hashSetOf(),
    var showcases: HashSet<Showcase> = hashSetOf(),
    var hasFreelance: Boolean = false,
    var achievements: HashSet<AchievementData> = AchievementType.values().map { AchievementData(1, it) }.toHashSet(),
)
