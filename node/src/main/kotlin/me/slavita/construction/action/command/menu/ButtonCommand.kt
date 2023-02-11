package me.slavita.construction.action.command.menu

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class ButtonCommand(player: Player, val action: () -> Unit) : CooldownCommand(player.user, 1) {
    override fun execute() {
        action()
    }
}
