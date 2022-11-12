package me.slavita.construction.action.command

import me.func.protocol.ui.dialog.*
import me.slavita.construction.action.DialogCommand
import org.bukkit.entity.Player

class GuideDialog(val user: Player) : DialogCommand(user.player) {
    override fun getDialog(): Dialog {
        return Dialog(
            Entrypoint(
                "0",
                "Гайд",
                Screen(
                    "Приветствие"
                ).buttons(
                    Button("Понятно").actions(
                        Action("/next")
                    )
                )
            )
        )
    }
}