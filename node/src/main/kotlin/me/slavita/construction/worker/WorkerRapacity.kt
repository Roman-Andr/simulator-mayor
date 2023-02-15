package me.slavita.construction.worker

import org.bukkit.ChatColor.GREEN

enum class WorkerRapacity(
    val title: String,
    val value: Double,
    val reliabilityMin: Int,
    val reliabilityMax: Int,
) {
    LOW(
        "${GREEN}Низкая",
        1.0,
        10,
        50
    ),
    MEDIUM(
        "${GREEN}Средняя",
        1.0,
        50,
        80,
    ),
    HIGH(
        "${GREEN}Высокая",
        1.0,
        80,
        100,
    )
}
