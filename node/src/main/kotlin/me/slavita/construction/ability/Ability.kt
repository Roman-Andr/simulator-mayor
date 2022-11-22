package me.slavita.construction.ability

import me.slavita.construction.player.User
import java.util.function.Consumer

interface Ability {
    fun apply(user: User)
}