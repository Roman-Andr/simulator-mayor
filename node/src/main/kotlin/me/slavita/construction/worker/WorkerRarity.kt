package me.slavita.construction.worker

import me.func.protocol.data.rare.DropRare
import org.bukkit.Material

enum class WorkerRarity(
    val title: String,
    val dropRare: DropRare,
    val description: String,
    val price: Long,
    val value: Double,
    val iconKey: String,
    val iconValue: String,
    val iconMaterial: Material = Material.CLAY_BALL,
) {
    COMMON("§7Обычный", DropRare.COMMON, "Обыкновенный строитель", 100, 1.0, "other", "f", Material.TOTEM),
    RARE("§2Редкий", DropRare.RARE, "Неплохой строитель", 10000, 1.0, "other", "2", Material.TOTEM),
    EPIC("§5Эпический", DropRare.EPIC, "Отличный строитель", 1000000, 1.0, "other", "5", Material.TOTEM),
    LEGENDARY("§6Легендарный", DropRare.LEGENDARY, "Превосходный строитель", 100000000, 1.0, "other", "6", Material.TOTEM)
}
