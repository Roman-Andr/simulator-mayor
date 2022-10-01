package me.slavita.construction.player

class Statistics(
    var money: Long,
    var level: Int,
    var experience: Double,
    var reputation: Double,

    var totalProjects: Int,
    var maxProjects: Int,
) {
    constructor() : this(1000000000000000000, 1, .0, .0, 0, 0)
}