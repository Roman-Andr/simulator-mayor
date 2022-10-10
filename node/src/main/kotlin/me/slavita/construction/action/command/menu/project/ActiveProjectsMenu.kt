package me.slavita.construction.action.command.menu.project

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import org.bukkit.ChatColor.AQUA
import org.bukkit.entity.Player
import java.util.stream.Collectors
import java.util.stream.Stream

class ActiveProjectsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return getBaseSelection(MenuInfo("Ваши активные проекты", StatsType.MONEY, 4, 5)).apply {
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    activeProjects.forEach {
                        this@storage.add(
                            ReactiveButton()
                                .item(ItemIcons.get("skyblock", "spawn"))
                                .title("Проект #${it.id}")
                                .hover(Stream.of(
                                    "${AQUA}ID: ${it.id}\n",
                                    "${AQUA}Награды: \n${it.rewards.joinToString("\n") { it.toString() }}\n"
                                ).collect(Collectors.joining()))
                                .special(it.structure is ClientStructure)
                                .onClick { _, _, _ ->
                                    Anime.close(player)
                                }
                        )
                    }
                }
            }
        }
    }
}