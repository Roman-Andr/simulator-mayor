package me.slavita.construction.worker

enum class WorkerRapacity(
    val title: String,
    val value: Double
) {
    LOW("§aНизкая", 1.0),
    MEDIUM("§eСредняя", 1.0),
    HIGH("§4Высокая", 1.0)
}
