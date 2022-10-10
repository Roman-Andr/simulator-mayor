package me.slavita.construction.player

class Statistics(
    var money: Long,
    var level: Int,
    experience: Int,
    var reputation: Double,

    var totalProjects: Int,
    var maxProjects: Int,
) {
    var experience = experience
        set(value) {
            var response = value
            if (response / 100 > 0) {
                level += response / 100
                response %= 100
            }
            field = response
        }

    constructor() : this(1000000000000000000, 1, 0, .0, 0, 0)
}