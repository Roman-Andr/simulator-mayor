package me.slavita.construction.ability

import me.slavita.construction.player.User

object FlyAbility : Ability {
    override fun apply(user: User) {
        user.player.allowFlight = true
    }
}