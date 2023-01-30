package me.slavita.construction.prepare

import me.slavita.construction.player.User

object WarningPrepare : IPrepare {
    override fun prepare(user: User) {
        user.data.run {
            if (hasFreelance) {
                user.leaveFreelance()
            }
        }
    }
}
