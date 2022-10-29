package me.slavita.construction.action.command.menu.lootbbox

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.User
import me.slavita.construction.player.lootbox.Lootbox

class OpenLootbox(val user: User, val lootbox: Lootbox) : CooldownCommand(user.player, 2) {
    override fun execute() {
        lootbox.open(user)
    }
}