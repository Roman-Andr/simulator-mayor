package me.slavita.construction.action.command.menu

import me.func.mod.reactive.ButtonClickHandler
import me.slavita.construction.action.CooldownCommand
import org.bukkit.entity.Player

class ButtonCommand(player: Player, val action: () -> Unit) : CooldownCommand(player, 1) {
    override fun execute() {
        action()
    }
}
