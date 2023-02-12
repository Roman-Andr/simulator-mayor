package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.func.protocol.ui.dialog.Action
import me.func.protocol.ui.dialog.Actions
import me.func.protocol.ui.dialog.Button
import me.func.protocol.ui.dialog.Dialog
import me.func.protocol.ui.dialog.Entrypoint
import me.func.protocol.ui.dialog.Screen
import me.slavita.construction.player.User

object GuidePrepare : IPrepare {
    var dialog: Dialog? = null

    override fun prepare(user: User) {
        Atlas.find("dialogs").apply {
            dialog = Dialog(
                Atlas.section("dialogs", "dialogs").map { id ->
                    val actionType = getString("dialogs.$id.action")

                    Entrypoint(
                        id.toString(),
                        getString("dialogs.$id.title"),
                        Screen(*getStringList("dialogs.$id.content").toTypedArray())
                            .buttons(
                                Button(
                                    when (actionType) {
                                        "CONTINUE" -> "Далее"
                                        else -> "Хорошо"
                                    }
                                ).actions(
                                    when (actionType) {
                                        "CONTINUE" -> Action.command("/dialog ${getString("command-key")}")
                                        else -> Action(Actions.CLOSE)
                                    }
                                )
                            )
                    )
                }
            )
        }
    }

    fun tryNext(user: User) {
        val entryPoint: String = if (
            false
        ) {
            "${user.data.trainStep}-not_complete"
        } else {
            user.data.trainStep++
            user.data.trainStep.toString()
        }

        dialog?.run {
            me.func.mod.ui.dialog.Dialog.dialog(user.player, this, entryPoint)
        }
    }
}
