package me.slavita.construction.world

class AmountItemProperties(properties: ItemProperties, val amount: Int) :
    ItemProperties(properties.type, properties.data)
