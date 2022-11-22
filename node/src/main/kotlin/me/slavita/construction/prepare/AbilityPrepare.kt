package me.slavita.construction.prepare

import me.slavita.construction.player.User

object AbilityPrepare : IPrepare {
    override fun prepare(user: User) {
        user.abilities.forEach {
            it.apply(user)
        }
    }
}