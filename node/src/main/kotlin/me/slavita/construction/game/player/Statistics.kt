package me.slavita.construction.game.player

data class Statistics(
    var money: Double,
    var level: Int,
    var experience: Double,
    var reputation: Double,

    var totalProjects: Int,
    var maxProjects: Int,
)