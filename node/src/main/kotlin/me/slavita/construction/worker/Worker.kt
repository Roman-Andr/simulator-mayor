package me.slavita.construction.worker

import me.slavita.construction.player.User

class Worker(
    val owner: User,
    val rarity: WorkerRarity,
    val skill: Double,
    val reliability: Double,
    val rapacity: WorkerRapacity
)