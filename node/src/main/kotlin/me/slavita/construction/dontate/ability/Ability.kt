package me.slavita.construction.dontate.ability

import me.slavita.construction.player.User

interface Ability {
    fun apply(user: User)
}