package me.slavita.construction.action.command.menu

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.User
import me.slavita.construction.prepare.GuidePrepare

class GuideDialog(user: User) : CooldownCommand(user, 2) {
    override fun execute() {
        GuidePrepare.tryNext(user)
    }
}