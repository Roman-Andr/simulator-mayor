package me.slavita.construction.action.command

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.app
import me.slavita.construction.player.City
import org.bukkit.entity.Player

class ChangeCity(val user: Player, val city: City) : CooldownCommand(user, 15 * 20) {
    override fun execute() {
        app.getUser(user).changeCity(city)
    }
}