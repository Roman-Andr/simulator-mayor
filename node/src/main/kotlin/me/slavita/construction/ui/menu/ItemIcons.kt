package me.slavita.construction.ui.menu

import dev.implario.bukkit.item.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemIcons {
	fun get(key: String, value: String, material: Material = Material.CLAY_BALL): ItemStack {
		return ItemBuilder(material)
			.nbt(key, value)
			.build()
	}
}