package me.slavita.construction.prepare

import me.slavita.construction.player.User
import me.slavita.construction.utils.deny

object WarningPrepare : IPrepare {
    override fun prepare(user: User) {
        user.data.run {
            if (hasFreelance) {
                hasFreelance = false
                if (reputation >= 100) reputation -= 100 else reputation = 0L
                user.player.deny("Вы вышли во время фриланс заказа. Штраф: 100 репутации")
            }
        }
    }
}
