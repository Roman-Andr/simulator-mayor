package me.slavita.construction.prepare

import me.slavita.construction.player.User
import me.slavita.construction.utils.deny

object WarningPrepare : IPrepare {
    override fun prepare(user: User) {
        user.data.run {
            if (hasFreelance) {
                user.leaveFreelance()
            }
        }
    }
}
