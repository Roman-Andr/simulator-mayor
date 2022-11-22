package me.slavita.construction.action.command

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.City
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class ChangeCity(val user: Player, val city: City) : CooldownCommand(user, 15 * 20) {
    override fun execute() {
        user.user.changeCity(city)
    }
}