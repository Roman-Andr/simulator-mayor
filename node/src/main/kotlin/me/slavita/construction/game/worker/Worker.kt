package me.slavita.construction.game.worker

import me.slavita.construction.game.player.User

class Worker(
    val owner: User,
    val rarity: WorkerRarityType,
    val skill: Double,
    val reliability: Double,
    val rapacity: RapacityType
) {
}