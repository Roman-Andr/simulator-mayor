package me.slavita.construction.player

import kotlin.math.pow

class Statistics(
	var money: Long,
	var level: Int,
	var experience: Long,
	var reputation: Double,
	var totalProjects: Int,
	var maxProjects: Int,
) {
	constructor() : this(10000000000, 1, 0, .0, 0, 0)
}