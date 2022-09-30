package me.slavita.construction.worker

import java.util.*

class Worker(
    val name: String,
    val rarity: WorkerRarity,
    val skill: Int,
    val reliability: Int,
    val rapacity: WorkerRapacity
) {
    val uuid = UUID.randomUUID()

    val sellPrice: Int
        get() { return 199 /* Формула */ }
}