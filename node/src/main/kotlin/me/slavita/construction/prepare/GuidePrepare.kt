package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.func.protocol.ui.dialog.Action
import me.func.protocol.ui.dialog.Actions
import me.func.protocol.ui.dialog.Button
import me.func.protocol.ui.dialog.Dialog
import me.func.protocol.ui.dialog.Entrypoint
import me.func.protocol.ui.dialog.Screen
import me.slavita.construction.player.User
import me.slavita.construction.ui.GuideEntry

object GuidePrepare : IPrepare {
    const val COMMAND_KEY = "e7c73529bb9e2ced61424b792f10019f"
    private var dialog: Dialog? = null
    private val dialogs = mutableListOf(
        GuideEntry("1", "Обучение", "CONTINUE", listOf(
            "Здравствуйте наш уважаемый мэр"
        )),
        GuideEntry("2", "Обучение", "CONTINUE", listOf(
            "Здесь находится ваш город, но в нём не хватает зданий",
            "Ваша цель - застроить его и разбогатеть"
        )),
        GuideEntry("3", "Обучение", "CLOSE", listOf(
            "В начале вам нужно начать строить первое здание самому"
        )),
        GuideEntry("3-not_complete", "Обучение", "CLOSE", listOf(
            "Вы не начали строить здание"
        )),
        GuideEntry("4", "Обучение", "CONTINUE", listOf(
            "Отлично, для строительства вам нужны блоки",
            "Держите, этого хватит на первое время",
            "После вам нужно будет покупать блоки в магазине"
        )),
        GuideEntry("5", "Обучение", "CLOSE", listOf(
            "С помощью полученных блоков постройте ваше первое здание"
        )),
        GuideEntry("5-not_complete", "Обучение", "CLOSE", listOf(
            "Вы ещё не достроили первое здание"
        )),
        GuideEntry("6", "Обучение", "CONTINUE", listOf(
            "Отлично, вы справились"
        )),
        GuideEntry("7", "Обучение", "CLOSE", listOf(
            "У вас появились первые деньги. Теперь вы можете нанимать рабочих",
            "Наймите 1 рабочего и возвращайтесь сюда"
        )),
        GuideEntry("7-not_complete", "Обучение", "CLOSE", listOf(
            "Вы не наняли ни одного рабочего"
        )),
        GuideEntry("8", "Обучение", "CLOSE", listOf(
            "Теперь начните строить здание, используя купленного рабочего"
        )),
        GuideEntry("8-not_complete", "Обучение", "CLOSE", listOf(
            "Вы не начали строить здание, используя рабочего"
        )),
        GuideEntry("9", "Обучение", "CLOSE", listOf(
            "Отлично! Пока вы будете играть, ваши рабочие будут строить новые здания.",
            "В конце постройки вам нужно будет собрать награду за постройку. Удачи!"
        )),
    )

    override fun prepare(user: User) {
        dialog = Dialog(
            dialogs.map { entry ->
                Entrypoint(
                    entry.id,
                    entry.title,
                    Screen(*entry.content.toTypedArray())
                        .buttons(
                            Button(
                                when (entry.action) {
                                    "CONTINUE" -> "Далее"
                                    else -> "Хорошо"
                                }
                            ).actions(
                                when (entry.action) {
                                    "CONTINUE" -> Action.command("/dialog $COMMAND_KEY")
                                    else -> Action(Actions.CLOSE)
                                }
                            )
                        )
                )
            }
        )
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
