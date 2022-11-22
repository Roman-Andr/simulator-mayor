package me.slavita.construction.booster

import me.func.protocol.data.emoji.Emoji

enum class BoosterType(
    val label: String,
    val title: String,
) {
    MONEY_BOOSTER("money", Emoji.DOLLAR),
    INCOME_BOOSTER("income", Emoji.COIN),
    EXP_BOOSTER("exp", Emoji.EXP),
    REPUTATION_BOOSTER("reputation", Emoji.RUBY),
    SPEED_BOOSTER("speed", Emoji.TIME),
}