package me.slavita.construction.prepare

import me.slavita.construction.player.User

object AbilityPrepare : IPrepare {
    override fun prepare(user: User) {
        user.data.abilities.forEach { ability ->
            ability.applyAction(user)
        }
    }
}