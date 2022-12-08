package me.slavita.construction.action.command.menu.worker

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.menu.lootbbox.BuyLootboxMenu
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class WorkerMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return choicer {
                title = "${GREEN}${BOLD}Рабочие"
                description = "Выбери нужный раздел"
                storage = mutableListOf(
                    button {
                        title = "${GOLD}${BOLD}Покупка\n${GOLD}${BOLD}работников"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE_LIGHT
                        item = ItemIcons.get("other", "guild_members_add")
                        onClick { _, _, _ ->
                            BuyLootboxMenu(player).closeAll(false).tryExecute()
                        }
                    },
                    button {
                        title = "${GOLD}${BOLD}Список\n${GOLD}${BOLD}работников"
                        hint = "Выбрать"
                        backgroundColor = GlowColor.BLUE
                        item = ItemIcons.get("other", "guild_members")
                        onClick { _, _, _ ->
                            WorkerTeamMenu(player).closeAll(false).tryExecute()
                        }
                    }
                )
            }
        }
    }
}