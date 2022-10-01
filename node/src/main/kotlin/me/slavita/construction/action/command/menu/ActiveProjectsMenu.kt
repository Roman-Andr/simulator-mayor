package me.slavita.construction.action.command.menu

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.ui.ItemIcons
import org.bukkit.entity.Player
import java.util.stream.Collectors
import java.util.stream.Stream

class ActiveProjectsMenu(player: Player) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = "Ваши активные проекты",
                vault = Emoji.DOLLAR,
                money = "Ваш баланс ${stats.money}",
                rows = 4,
                columns = 5,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    activeProjects.forEach {
                            this@storage.add(
                                ReactiveButton()
                                    .item(ItemIcons.get("", ""))
                                    .title("Проект #${it.id}")
                                    .hover(Stream.of(
                                        "§aID: ${it.id}\n",
                                        "§eНаграда: ${it.stats.reward}\n"
                                    ).collect(Collectors.joining()))
                                    .special(it.structure is ClientStructure)
                                    .onClick { _, _, _ ->
                                        Anime.close(player)
                                    }
                            )
                        }
                }
            )
        }
    }
}