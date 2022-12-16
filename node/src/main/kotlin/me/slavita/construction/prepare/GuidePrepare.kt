package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.func.protocol.ui.dialog.*
import me.slavita.construction.player.User
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

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
                                        else       -> "Хорошо"
                                    }
                                ).actions(
                                    when (actionType) {
                                        "CONTINUE" -> Action.command("/dialog ${getString("command-key")}")
                                        else       -> Action(Actions.CLOSE)
                                    }
                                )
                            )
                    )
                }
            )
        }
    }

    fun tryNext(player: Player) {
        val user = player.user

        val entryPoint: String = if (
            false
        ) {
            "${user.data.statistics.trainStep}-not_complete"
        } else {
            user.data.statistics.trainStep++
            user.data.statistics.trainStep.toString()
        }

        dialog?.run {
            me.func.mod.ui.dialog.Dialog.dialog(player, this, entryPoint)
        }
    }
}