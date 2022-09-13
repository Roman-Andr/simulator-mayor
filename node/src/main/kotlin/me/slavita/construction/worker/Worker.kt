package me.slavita.construction.worker

import me.slavita.construction.player.User

class Worker(
    val name: String,
    val rarity: WorkerRarity,
    val skill: Int,
    val reliability: Int,
    val rapacity: WorkerRapacity
)