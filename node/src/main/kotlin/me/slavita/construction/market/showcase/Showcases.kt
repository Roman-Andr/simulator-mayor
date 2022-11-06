package me.slavita.construction.market.showcase

import org.bukkit.Material

object Showcases {
	val showcases = hashSetOf<ShowcaseProperties>(
//		ShowcaseProperties(1, "concrete", "Бетон", Material.CONCRETE, Material.CONCRETE_POWDER),
//		ShowcaseProperties(2, "glass", "Стекло", Material.GLASS, Material.STAINED_GLASS, Material.STAINED_GLASS_PANE),
	)

	init {
		showcases.add(
			ShowcaseProperties(
				6, "deco", "Декорации",
				*Material.values().filter filter@{ material ->
//					showcases.forEach {
//						if (it.materials.contains(material)) return@filter false
//					}
					true
				}.toTypedArray()
			)
		)
	}
}