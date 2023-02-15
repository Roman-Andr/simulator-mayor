package me.slavita.construction.ui

import org.bukkit.ChatColor.*

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
        "${GRAY}Мусорка",
        "trash",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    CITY_HALL(
        "${GREEN}Мэрия",
        "city-hall",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    CLAIM_BANNER(
        "${GOLD}Отдать блоки",
        "",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    STORAGE_UPGRADE(
        "${GREEN}Склад",
        "storage-upgrade",
        8,
        35,
        2.5,
        2.0,
        listOf(1.0)
    ),
    SPEED_PLACE(
        "${AQUA}Ускоритель",
        "speed",
        7,
        30,
        1.0,
        2.0,
        listOf(0.4)
    ),
    CELL_BANNER(
        "${GOLD}Клетка",
        "",
        8,
        42,
        4.0,
        2.0,
        listOf(2.0)
    ),
    SHOWCASE(
        "\n${WHITE}Витрина ${YELLOW}[ ПКМ ]",
        "",
        80,
        40,
        0.0,
        2.0,
        listOf(0.9, 0.9)
    ),
    WORKER(
        "${GREEN}Управление\n${GREEN}Работниками",
        "",
        42,
        16,
        3.0,
        2.0,
        listOf(0.5, 0.5)
    ),
    DAILY(
        "${GREEN}Ежедневные\n§aНаграды",
        "",
        42,
        16,
        3.0,
        2.0,
        listOf(0.5, 0.5)
    ),
    LOCATIONS(
        "${GOLD}Локации",
        "",
        42,
        8,
        2.5,
        2.0,
        listOf(0.5)
    ),
    PROJECTS(
        "${GOLD}Проекты",
        "",
        42,
        8,
        2.5,
        2.0,
        listOf(0.5)
    ),
    BANK(
        "${GOLD}Банк",
        "",
        42,
        8,
        2.5,
        2.0,
        listOf(0.5)
    ),
    GUIDE(
        "${GREEN}${BOLD}Обучение",
        "",
        42,
        8,
        2.5,
        2.0,
        listOf(0.5)
    ),
}
