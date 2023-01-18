package me.slavita.construction.ui

import implario.humanize.Humanize

enum class HumanizableValues(val oneValue: String, val twoValue: String, val fiveValue: String) {
    CRI_MONEY("кристаллик", "кристаллика", "кристалликов"),
    SECOND("секунду", "секунды", "секунд"),
    LOOTBOX("лутбокс", "лутбокса", "лутбоксов"),
    BLOCK("блок", "блока", "блоков");

    fun get(value: Number): String {
        return "$value ${Humanize.plurals(oneValue, twoValue, fiveValue, value.toInt())}"
    }
}