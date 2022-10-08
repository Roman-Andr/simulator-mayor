package me.slavita.construction.market

import org.bukkit.Material

object Showcases {
    val showcases = hashSetOf(
        ShowcaseProperties("concrete", "Бетон", Material.CONCRETE, Material.CONCRETE_POWDER),
        ShowcaseProperties("glass", "Стекло", Material.GLASS, Material.STAINED_GLASS, Material.STAINED_GLASS_PANE),
        ShowcaseProperties("flowers", "Цветы",
            Material.FLOWER_POT,
            Material.YELLOW_FLOWER,
            Material.RED_ROSE,
            Material.DOUBLE_PLANT,
            Material.CHORUS_PLANT
        ),
        ShowcaseProperties("wool", "Шерсть", Material.WOOL),
        ShowcaseProperties("wood", "Дерево",
            Material.WOOD,
            Material.ACACIA_STAIRS,
            Material.BIRCH_WOOD_STAIRS,
            Material.SPRUCE_WOOD_STAIRS,
            Material.JUNGLE_WOOD_STAIRS
        ),
    )

    init {
        showcases.add(
            ShowcaseProperties("deco", "Декорации",
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