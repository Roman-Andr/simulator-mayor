package me.slavita.construction.worker

enum class WorkerRapacity(
    val title: String,
    val value: Double,
    val reliabilityMin: Int,
    val reliabilityMax: Int,
) {
    LOW(
        "§aНизкая",
        1.0,
        10,
        50
    ),
    MEDIUM(
        "§aСредняя",
        1.0,
        50,
        80,
    ),
    HIGH(
        "§aВысокая",
        1.0,
        80,
        100,
    )
}
