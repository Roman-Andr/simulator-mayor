package me.slavita.construction.ui

enum class BannerSamples(
    val content: String,
    val weight: Int,
    val height: Int,
    val offset: Double,
    val carvedSize: Double,
    val lineSizes: List<Double>,
) {
    TRASH(
        "§7Мусорка",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    CITY_HALL(
        "§aМэрия",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    CLAIM_BANNER(
        "§6Отдать блоки",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    STORAGE_UPGRADE(
        "§aСклад",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    SPEED_PLACE(
        "§bУскоритель",
        7,
        30,
        1.0,
        2.0,
        listOf(0.4)
    ),
    AFK_ZONE(
        "§cАФК-Зона",
        8,
        42,
        4.0,
        2.0,
        listOf(2.0)
    ),
    CELL_BANNER(
        "§6Клетка",
        8,
        42,
        4.0,
        2.0,
        listOf(2.0)
    ),
    SHOWCASE(
        "\n§bВитрина §8[§6ПКМ§8]",
        80,
        40,
        0.0,
        2.0,
        listOf(0.9, 0.9)
    )
}
