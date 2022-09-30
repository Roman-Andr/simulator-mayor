package me.slavita.construction.action.command

import me.func.mod.ui.menu.Openable
import me.slavita.construction.action.MenuCommand
import me.func.mod.Anime
import me.func.mod.conversation.data.LootDrop
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.Glow
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.entity.Player

class ProjectsCommand(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        val user = app.getUser(player)

        val storage = mutableListOf(
            ReactiveButton()
                .title("Получить проект")
                .description("Вы сами будете его строить")
                .item(ItemIcons.get("skyblock", "crafts"))
                .onClick { _, _, _ ->
                    Anime.close(player)
                    app.mainWorld.playerBuild(player)
                },
            ReactiveButton()
                .title("Получить проект")
                .description("За вас построят все ваши строители")
                .item(ItemIcons.get("skyblock", "crafts"))
                .onClick { _, _, _ ->
                    Anime.close(player)
                    app.mainWorld.playerWorkerBuild(player)
                }
        )

        return Selection(
            title = "Ваши строители",
            money = "Ваш баланс ${user.stats.money}",
            rows = 4,
            columns = 5,
            storage = storage
        )
    }
}