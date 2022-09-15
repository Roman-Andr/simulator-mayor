package me.slavita.construction.worker

import me.slavita.construction.player.User

class Worker(
    val name: String,
    val rarity: WorkerRarity,
    var active: Boolean,
    val skill: Int,
    val reliability: Int,
    val rapacity: WorkerRapacity
)