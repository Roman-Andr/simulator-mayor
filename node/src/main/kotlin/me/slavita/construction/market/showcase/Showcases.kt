package me.slavita.construction.market.showcase

import org.bukkit.Material

object Showcases {
	val showcases = hashSetOf(
		ShowcaseProperties(1, "concrete", "Бетон", Material.CONCRETE, Material.CONCRETE_POWDER),
		ShowcaseProperties(2, "glass", "Стекло", Material.GLASS, Material.STAINED_GLASS, Material.STAINED_GLASS_PANE),
		ShowcaseProperties(
			3, "flowers", "Цветы",
			Material.FLOWER_POT,
			Material.YELLOW_FLOWER,
			Material.RED_ROSE,
			Material.DOUBLE_PLANT,
			Material.CHORUS_PLANT
		),
		ShowcaseProperties(4, "wool", "Шерсть", Material.WOOL),
		ShowcaseProperties(
			5, "wood", "Дерево",
			Material.WOOD,
			Material.ACACIA_STAIRS,
			Material.BIRCH_WOOD_STAIRS,
			Material.SPRUCE_WOOD_STAIRS,
			Material.JUNGLE_WOOD_STAIRS
		),
	)

	init {
		showcases.add(
			ShowcaseProperties(
				6, "deco", "Декорации",
				*Material.values().filter filter@{ material ->
					showcases.forEach {
						if (it.materials.contains(material)) return@filter false
					}
					true
				}.toTypedArray()
			)
		)
	}
}