package me.slavita.construction.worker

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

    fun generate(rarity: WorkerRarity, amount: Int): MutableList<Worker> {
        return mutableListOf<Worker>().apply {
            repeat(amount) {
                val rapacity = WorkerRapacity.values().random()
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

    fun generate(rarity: WorkerRarity) = WorkerGenerator.generate(rarity, 1).first()
}
