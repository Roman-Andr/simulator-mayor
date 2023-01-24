package me.slavita.construction.action.command.menu.city

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toLevel
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.click
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class StorageUpgrade(val player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {
            title = "${GREEN}${BOLD}Склад"
            description = "Управление складом"
            storage = mutableListOf(
                button {
                    title = "${GREEN}${BOLD}Улучшить"
                    hint = "Улучшить"
                    backgroundColor = GlowColor.GREEN
                    hover = """
                            ${GREEN}При улучшении:
                              ${AQUA}Уровень: ${GRAY}${user.data.blocksStorage.level.toLevel()} ${BOLD}-> ${GREEN}${(user.data.blocksStorage.level + 1).toLevel()}
                              ${GREEN}Вместимость: ${GRAY}${user.data.blocksStorage.limit} блоков ${BOLD}-> ${GOLD}${user.data.blocksStorage.nextLimit} блоков
                            
                            ${BOLD}${WHITE}Стоимость: ${GREEN}${user.data.blocksStorage.upgradePrice.toMoneyIcon()}
                        """.trimIndent()
                    item = Icons.get("other", "anvil")
                    click { _, _, _ ->
                        user.data.blocksStorage.upgrade()
                        Anime.close(player)
                    }
                }
            )
        }
    }
}