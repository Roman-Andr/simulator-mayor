package me.slavita.construction.ui.achievements

data class AchievementData(
    val type: AchievementType,
    var level: Int = 0,
    var lastValue: Long = 0,
    var expectValue: Long = type.formula(1),
)
