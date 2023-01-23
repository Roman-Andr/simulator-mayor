package me.slavita.construction.prepare

import me.slavita.construction.player.User
import me.slavita.construction.utils.nextTick

object AbilityPrepare : IPrepare {
    override fun prepare(user: User) {
        nextTick {
            user.data.abilities.forEach { ability ->
                ability.applyAction(user)
            }
        }
    }
}
