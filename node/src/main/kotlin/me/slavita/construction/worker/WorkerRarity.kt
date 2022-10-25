package me.slavita.construction.worker

import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import me.func.protocol.data.rare.DropRare
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.ChatColor.AQUA
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
		"${AQUA}Обычный",
		GlowColor.BLUE,
		DropRare.COMMON,
		"Обыкновенный строитель",
		100,
		1.0,
		"other",
		"f",
		Material.TOTEM
	),
	RARE(
		"${AQUA}Редкий",
		GlowColor.GREEN_MIDDLE,
		DropRare.RARE,
		"Неплохой строитель",
		10000,
		1.0,
		"other",
		"2",
		Material.TOTEM
	),
	EPIC(
		"${AQUA}Эпический",
		GlowColor.PURPLE,
		DropRare.EPIC,
		"Отличный строитель",
		1000000,
		1.0,
		"other",
		"5",
		Material.TOTEM
	),
	LEGENDARY(
		"${AQUA}Легендарный",
		GlowColor.ORANGE,
		DropRare.LEGENDARY,
		"Превосходный строитель",
		100000000,
		1.0,
		"other",
		"6",
		Material.TOTEM
	);

	fun getIcon(): ItemStack {
		return ItemIcons.get(iconKey, iconValue, iconMaterial)
	}
}
