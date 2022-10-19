package me.slavita.construction.worker

import me.func.atlas.Atlas
import kotlin.random.Random

object WorkerGenerator {
    private val names = Atlas.find("worker").getList("names")

    fun generate(rarity: WorkerRarity, amount: Int): MutableList<Worker> {
        val values = Atlas.find("worker").getMapList("rapacity").random().values
        val rapacity = WorkerRapacity(
            values.elementAt(0) as String,
            values.elementAt(1) as Double,
            values.elementAt(2) as Int,
            values.elementAt(3) as Int)
        return mutableListOf<Worker>().apply {
            repeat(amount) {
                add(
                    Worker(
                        names.shuffled()[0] as String,
                        rarity,
                        1,
                        1,
                        Random.nextInt(rapacity.reliabilityMin, rapacity.reliabilityMax),
                        rapacity
                    )
                )
            }
        }
    }
}