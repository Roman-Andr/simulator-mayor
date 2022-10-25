package me.slavita.construction.ui.menu

import me.func.protocol.data.emoji.Emoji

enum class StatsType(val title: String, val vault: String) {
	MONEY("баланс", Emoji.DOLLAR),
	CREDIT("кредит", Emoji.DOLLAR),
	LEVEL("уровень", Emoji.UP)
}
