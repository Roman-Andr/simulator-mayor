package me.slavita.construction.player

import me.slavita.construction.utils.PlayerExtensions.accept

class CityHall(val user: User) {
    var level: Int = 1
    val upgradePrice
        get() = level * 1000L
    val income
        get() = level * 1000L
    val nextIncome
        get() = (level + 1) * 1000L

    init {
        user.income += income
    }

    fun upgrade() {
        user.tryPurchase(upgradePrice) {
            user.income -= income
            level++
            user.income += income
            user.player.accept("Вы успешно улучшили Мэрию")
        }
    }
}