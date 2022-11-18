package me.slavita.construction.reward.daily

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.button
import me.slavita.construction.reward.Reward
import me.slavita.construction.ui.menu.ItemIcons

class DailyReward(val recieved: Boolean, val reward: Reward) {
    val button: ReactiveButton
        get() {
            return button {
                item = ItemIcons.get("other", "info")
                title = "Мега подарок"
            }
        }
}