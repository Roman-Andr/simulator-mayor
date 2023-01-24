package me.slavita.construction.action.command.menu.worker

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.WORKER_INFO
import me.slavita.construction.utils.click
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class WorkerMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${GREEN}${BOLD}Рабочие"
                description = "Выбери нужный раздел"
                info = WORKER_INFO
                storage = mutableListOf(
                    button {
                        title = "${GOLD}${BOLD}Покупка\n${GOLD}${BOLD}работников"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        item = Icons.get("other", "guild_members_add")
                        click { _, _, _ ->
                            BuyLootboxMenu(player).keepHistory().tryExecute()
                        }
                    },
                    button {
                        title = "${GOLD}${BOLD}Список\n${GOLD}${BOLD}работников"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE
                        item = Icons.get("other", "guild_members")
                        click { _, _, _ ->
                            WorkerTeamMenu(player).keepHistory().tryExecute()
                        }
                    }
                )
            }
        }
    }
}

