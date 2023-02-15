package me.slavita.construction.ui.menu

import me.func.protocol.data.emoji.Emoji

enum class StatsType(val title: String, val vault: String) {
    MONEY("Баланс", Emoji.COIN),
    CREDIT("Кредит", Emoji.COIN),
    LEVEL("Уровень", Emoji.UP)
}
