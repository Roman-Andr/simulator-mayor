package me.slavita.construction.worker

enum class WorkerRarity(
    val title: String,
    val description: String,
    val value: Double
) {
    COMMON("Обычный", "Обыкновенный строитель", 1.0),
    RARE("Редкий", "Неплохой строитель", 1.0),
    EPIC("Эпический", "Отличный строитель", 1.0),
    LEGENDARY("Легендарный", "Превосходный строитель", 1.0)
}
