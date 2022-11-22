package me.slavita.construction.dontate.ability

import me.slavita.construction.player.User
import java.util.function.Consumer

interface Ability {
    fun apply(user: User)
}