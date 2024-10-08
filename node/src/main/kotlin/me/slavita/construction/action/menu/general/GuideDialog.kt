package me.slavita.construction.action.menu.general

import me.slavita.construction.action.command.CooldownCommand
import me.slavita.construction.prepare.GuidePrepare
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class GuideDialog(player: Player) : CooldownCommand(player.user, 2) {

    override fun execute() {
        GuidePrepare.tryNext(user)
    }
}
