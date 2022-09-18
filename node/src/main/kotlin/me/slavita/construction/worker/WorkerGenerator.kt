package me.slavita.construction.worker

import kotlin.random.Random

object WorkerGenerator {
    val names = listOf<String>(
        "Григорий", "Андрей", "Арсений", "Степан", "Владислав", "Никита", "Давид", "Ярослав", "Евгений", "Матвей", "Фёдор", "Николай",
        "Алексей", "Артемий", "Виктор", "Даниил", "Денис", "Егор", "Игорь", "Леонид", "Павел", "Петр", "Руслан", "Сергей", "Семён", "Тимофей")

    fun generate(rarity: WorkerRarity): Worker {
        val rapacity = WorkerRapacity.values().asList().asSequence().shuffled().elementAt(0)
        return Worker(
            names.shuffled()[0],
            rarity,
            false,
            1,
            when(rapacity) {
                WorkerRapacity.LOW -> Random.nextInt(10, 50)
                WorkerRapacity.MEDIUM -> Random.nextInt(50, 80)
                WorkerRapacity.HIGH -> Random.nextInt(80, 100)
            },
            rapacity)

    }
}