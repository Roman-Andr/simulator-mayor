package me.slavita.construction.ui

enum class BannerSamples(
    val content: String,
    val label: String,
    val weight: Int,
    val height: Int,
    val offset: Double,
    val carvedSize: Double,
    val lineSizes: List<Double>,
) {
    TRASH(
        "§7Мусорка",
        "trash",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    CITY_HALL(
        "§aМэрия",
        "city-hall",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    CLAIM_BANNER(
        "§6Отдать блоки",
        "",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    STORAGE_UPGRADE(
        "§aСклад",
        "storage-upgrade",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    SPEED_PLACE(
        "§bУскоритель",
        "speed",
        7,
        30,
        1.0,
        2.0,
        listOf(0.4)
    ),
    AFK_ZONE(
        "§cАФК-Зона",
        "afk-zone",
        8,
        42,
        4.0,
        2.0,
        listOf(2.0)
    ),
    CELL_BANNER(
        "§6Клетка",
        "",
        8,
        42,
        4.0,
        2.0,
        listOf(2.0)
    ),
    SHOWCASE(
        "\n§bВитрина §8[§6ПКМ§8]",
        "",
        80,
        40,
        0.0,
        2.0,
        listOf(0.9, 0.9)
    ),
    WORKER(
        "§aУправление\n§aРаботниками",
        "",
        42,
        16,
        3.0,
        2.0,
        listOf(0.5, 0.5)
    ),
    DAILY(
        "§aЕжедневные\n§aНаграды",
        "",
        42,
        16,
        3.0,
        2.0,
        listOf(0.5, 0.5)
    ),
    LOCATIONS(
        "§6Локации",
        "",
        42,
        8,
        2.5,
        2.0,
        listOf(0.5)
    ),
    PROJECTS(
        "§6Проекты",
        "",
        42,
        8,
        2.5,
        2.0,
        listOf(0.5)
    ),
    BANK(
        "§6Банк",
        "",
        42,
        8,
        2.5,
        2.0,
        listOf(0.5)
    ),
    GUIDE(
        "§a§lОбучение",
        "",
        42,
        8,
        2.5,
        2.0,
        listOf(0.5)
    ),
}
