package me.slavita.construction.city

class CityHall {
    var level: Int = 1
    val upgradePrice
        get() = level * 1000L
    val income
        get() = level * 100L
    val nextIncome
        get() = (level + 1) * 100L
}
