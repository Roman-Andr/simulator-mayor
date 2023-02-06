package me.slavita.construction.worker

import me.func.atlas.Atlas
import kotlin.random.Random

object WorkerGenerator {
    private val names = listOf(
        "Григорий",
        "Андрей",
        "Арсений",
        "Владислав",
        "Евгений",
        "Ярослав",
        "Матвей",
        "Фёдор",
        "Николай",
        "Алексей",
        "Артём",
        "Виктор",
        "Даниил",
        "Денис",
        "Егор",
        "Игорь",
        "Леонид",
        "Павел",
        "Пётр",
        "Руслан",
        "Сергей",
        "Семён",
        "Тимофей",
    )
    val workers = Atlas.find("worker").getMapList("rapacity")

    fun generate(rarity: WorkerRarity, amount: Int): MutableList<Worker> {
        val values = workers.random().values
        val rapacity = WorkerRapacity(
            values.elementAt(0) as String,
            values.elementAt(1) as Double,
            values.elementAt(2) as Int,
            values.elementAt(3) as Int
        )
        return mutableListOf<Worker>().apply {
            repeat(amount) {
                add(
                    Worker(
                        names.shuffled()[0],
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
