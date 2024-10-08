package me.slavita.construction.action.command

import me.slavita.construction.city.City
import me.slavita.construction.player.User

class ChangeCity(override val user: User, val city: City) : CooldownCommand(user, 15 * 20) {
    override fun execute() {
        user.changeCity(city)
    }
}
