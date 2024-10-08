package me.slavita.construction.worker

import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import me.func.protocol.data.rare.DropRare
import me.slavita.construction.ui.menu.Icons
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.LIGHT_PURPLE
import org.bukkit.ChatColor.WHITE
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class WorkerRarity(
    val title: String,
    val color: RGB,
    val dropRare: DropRare,
    val description: String,
    val price: Long,
    val value: Double,
    val iconKey: String,
    val iconValue: String,
    val iconMaterial: Material = Material.CLAY_BALL,
) {
    COMMON(
        "${WHITE}${BOLD}Обычный",
        GlowColor.BLUE,
        DropRare.COMMON,
        "Обыкновенный",
        100,
        1.0,
        "other",
        "f",
        Material.TOTEM
    ),
    RARE(
        "${DARK_GREEN}${BOLD}Редкий",
        GlowColor.GREEN_LIGHT,
        DropRare.RARE,
        "Неплохой",
        10000,
        1.0,
        "other",
        "2",
        Material.TOTEM
    ),
    EPIC(
        "${LIGHT_PURPLE}${BOLD}Эпический",
        GlowColor.PURPLE,
        DropRare.EPIC,
        "Отличный",
        1000000,
        1.0,
        "other",
        "5",
        Material.TOTEM
    ),
    LEGENDARY(
        "${GOLD}${BOLD}Легендарный",
        GlowColor.ORANGE,
        DropRare.LEGENDARY,
        "Превосходный",
        100000000,
        1.0,
        "other",
        "6",
        Material.TOTEM
    );

    fun getIcon(): ItemStack {
        return Icons.get(iconKey, iconValue, false, iconMaterial)
    }
}
