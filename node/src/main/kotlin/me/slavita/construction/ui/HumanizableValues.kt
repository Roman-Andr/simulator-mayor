package me.slavita.construction.ui

import implario.humanize.Humanize

enum class HumanizableValues(val oneValue: String, val twoValue: String, val fiveValue: String) {
    CRI_MONEY("кристаллик", "кристаллика", "кристалликов");

    fun get(value: Int): String {
        return Humanize.plurals(oneValue, twoValue, fiveValue, value)
    }
}