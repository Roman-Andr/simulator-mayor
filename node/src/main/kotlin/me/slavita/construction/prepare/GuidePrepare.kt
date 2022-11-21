package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.func.protocol.ui.dialog.*
import me.slavita.construction.app
import me.slavita.construction.player.User
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
        val user = app.getUser(player)
        val entryPoint: String
        val step = user.stats.trainStep

        entryPoint = if (
            step == 3 && user.currentCity.projects.size == 0 ||
            step == 4 && user.workers.size == 0
        ) {
            "${step}-not_complete"
        } else {
            user.stats.trainStep++
            user.stats.trainStep.toString()
        }

        dialog?.run {
            println(entryPoint)
            me.func.mod.ui.dialog.Dialog.dialog(player, this, entryPoint)
        }
    }
}