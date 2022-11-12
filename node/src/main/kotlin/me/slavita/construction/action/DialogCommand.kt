package me.slavita.construction.action

import me.func.mod.Anime
import me.func.mod.ui.dialog.Dialog
import me.func.mod.ui.menu.Openable
import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.app
import org.bukkit.entity.Player

abstract class DialogCommand(player: Player) : CooldownCommand(player, 1) {
    private var close = true

    protected abstract fun getDialog(): me.func.protocol.ui.dialog.Dialog

    override fun execute() {
        Dialog.sendDialog(player, getDialog())
        Dialog.openDialog(player, app.getUser(player).dialogId.toString())
    }
}