package me.slavita.construction.booster

enum class BoosterType(
    val label: String,
    val title: String,
) {
    MONEY_BOOSTER("money", "Бустер денег"),
    EXP_BOOSTER("exp", "Бустер опыта"),
    REPUTATION_BOOSTER("reputation", "Бустер репутации"),
    SPEED_BOOSTER("speed", "Бустер скорости"),
}