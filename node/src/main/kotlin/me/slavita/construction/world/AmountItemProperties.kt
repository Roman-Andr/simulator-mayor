package me.slavita.construction.world

import org.bukkit.Material

class AmountItemProperties(properties: ItemProperties, val amount: Int) : ItemProperties(properties.type, properties.data)
