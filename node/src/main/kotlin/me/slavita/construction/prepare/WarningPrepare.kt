package me.slavita.construction.prepare

import me.slavita.construction.player.User
import me.slavita.construction.utils.deny

object WarningPrepare : IPrepare {
    override fun prepare(user: User) {
        user.data.run {
            if (hasFreelance) {
                hasFreelance = false
                if (statistics.reputation >= 100) statistics.reputation -= 100 else statistics.reputation = 0.0
                user.player.deny("Вы вышли во время фриланс заказа. Штраф: 100 репутации")
            }
        }
    }
}
