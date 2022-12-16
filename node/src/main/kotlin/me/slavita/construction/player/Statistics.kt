package me.slavita.construction.player

class Statistics(
    var money: Long = 0,
    var level: Int = 1,
    var experience: Long = 0,
    var reputation: Double = 0.0,
    var totalProjects: Int = 0,
    var maxProjects: Int = 0,
    var speed: Float = 0.2F,
    var trainStep: Int = 0,
    var nextDay: Int = 1,
    var nextTakeDailyReward: Long = 0,
)