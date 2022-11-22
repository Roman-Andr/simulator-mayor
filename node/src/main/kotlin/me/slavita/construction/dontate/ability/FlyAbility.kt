package me.slavita.construction.dontate.ability

import me.slavita.construction.player.User

object FlyAbility : Ability {
    override fun apply(user: User) {
        user.player.allowFlight = true
    }
}