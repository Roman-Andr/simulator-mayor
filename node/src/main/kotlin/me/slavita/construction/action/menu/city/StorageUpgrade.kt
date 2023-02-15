package me.slavita.construction.action.menu.city

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.ui.Formatter.toLevel
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.STORAGE_INFO
import me.slavita.construction.utils.click
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player

class StorageUpgrade(val player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {
            title = "${GREEN}${BOLD}Склад"
            info = STORAGE_INFO
            description = "Управление складом"
            storage = mutableListOf(
                button {
                    title = "${GOLD}${BOLD}Улучшить"
                    hint = "Улучшить"
                    backgroundColor = GlowColor.GREEN
                    hover = """
                            ${GREEN}При улучшении:
                              ${WHITE}Уровень: ${GRAY}${user.data.blocksStorage.level.toLevel()} $BOLD-> ${GREEN}${(user.data.blocksStorage.level + 1).toLevel()}
                              ${WHITE}Вместимость: ${GRAY}${user.data.blocksStorage.limit} блоков $BOLD-> ${GOLD}${user.data.blocksStorage.nextLimit} блоков
                            
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
