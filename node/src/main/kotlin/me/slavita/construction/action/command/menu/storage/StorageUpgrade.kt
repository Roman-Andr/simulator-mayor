package me.slavita.construction.action.command.menu.storage

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toIncomeIcon
import me.slavita.construction.ui.Formatter.toLevel
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.*
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class StorageUpgrade(val player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {
            title = "${GREEN}${BOLD}Склад"
            description = "Управление складом"
            storage = mutableListOf(
                button {
                    title = "${AQUA}${BOLD}Улучшить"
                    hint = "Улучшить"
                    backgroundColor = GlowColor.GREEN
                    hover = """
                            ${GREEN}При улучшении:
                              ${AQUA}Уровень: ${GRAY}${user.blocksStorage.level.toLevel()} ${BOLD}-> ${GREEN}${(user.blocksStorage.level + 1).toLevel()}
                              ${GREEN}Вместимость: ${GRAY}${user.blocksStorage.limit} блоков ${BOLD}-> ${GOLD}${user.blocksStorage.nextLimit} блоков
                            
                            ${BOLD}${WHITE}Стоимость: ${GREEN}${user.blocksStorage.upgradePrice.toMoneyIcon()}
                        """.trimIndent()
                    item = ItemIcons.get("other", "crafting")
                    onClick { _, _, _ ->
                        user.blocksStorage.upgrade()
                        Anime.close(player)
                    }
                }
            )
        }
    }
}