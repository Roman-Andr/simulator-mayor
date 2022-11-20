package me.slavita.construction.action.command.menu

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.prepare.GuidePrepare
import org.bukkit.entity.Player

class GuideDialog(val user: Player) : CooldownCommand(user, 2) {
    override fun execute() {
        GuidePrepare.tryNext(player)
    }
}