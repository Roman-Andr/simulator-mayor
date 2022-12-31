package me.slavita.construction.action.command

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.City
import me.slavita.construction.player.User

class ChangeCity(override val user: User, val city: City) : CooldownCommand(user, 15 * 20) {
    override fun execute() {
        user.changeCity(city)
    }
}