package me.slavita.construction.action.command.menu.project

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.ui.Formatter.toMoney
import org.bukkit.ChatColor.AQUA
import org.bukkit.entity.Player
import java.util.stream.Collectors
import java.util.stream.Stream

class ActiveProjectsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = "Ваши активные проекты",
                vault = Emoji.DOLLAR,
                money = "Ваш баланс ${stats.money.toMoney()}",
                rows = 4,
                columns = 5,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    activeProjects.forEach {
                            this@storage.add(
                                ReactiveButton()
                                    .item(ItemIcons.get("skyblock", "spawn"))
                                    .title("Проект #${it.id}")
                                    .hover(Stream.of(
                                        "${AQUA}ID: ${it.id}\n",
                                        "${AQUA}Награда: ${it.stats.reward}\n"
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