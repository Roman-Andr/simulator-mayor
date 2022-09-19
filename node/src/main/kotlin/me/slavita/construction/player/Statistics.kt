package me.slavita.construction.player

data class Statistics(
    var money: Double,
    var level: Int,
    var experience: Double,
    var reputation: Double,

    var totalProjects: Int,
    var maxProjects: Int,
) {
    constructor() : this(.0, 1, .0, .0, 0, 0)
}