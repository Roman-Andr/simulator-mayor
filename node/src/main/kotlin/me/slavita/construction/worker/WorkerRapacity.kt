package me.slavita.construction.worker

import org.bukkit.ChatColor.*

enum class WorkerRapacity(
    val title: String,
    val value: Double
) {
    LOW("${AQUA}Низкая", 1.0),
    MEDIUM("${AQUA}Средняя", 1.0),
    HIGH("${AQUA}Высокая", 1.0)
}
