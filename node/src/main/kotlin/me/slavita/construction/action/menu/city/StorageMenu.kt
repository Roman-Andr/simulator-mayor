package me.slavita.construction.action.menu.city

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.utils.STORAGE_INFO
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.size
import me.slavita.construction.utils.user
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class StorageMenu(player: Player) : MenuCommand(player) {
    private val blocksStorage = player.user.data.blocksStorage
    private var selection = Selection()

    override fun getMenu(): Openable {
        selection = selection {
            title = "${GREEN}${BOLD}Склад"
            info = STORAGE_INFO
            size(5, 14)
            money = getFreeSpace()
            storage = blocksStorage.blocks.mapM { entry ->
                button {
                    item = entry.key.createItemStack(1)
                    hover = getHover(entry)
                    hint = " "
                    onLeftClick { _, _, _ ->
                        removeItems(64, entry, this)
                    }
                    onRightClick { _, _, _ ->
                        removeItems(entry.value, entry, this)
                    }
                }
            }
        }

        return selection
    }

    private fun getFreeSpace() = "Занято ${blocksStorage.itemsCount} из ${blocksStorage.limit} блоков"

    private fun getHover(entry: Map.Entry<ItemProperties, Int>) = """
        ${GREEN}${LanguageHelper.getItemDisplayName(entry.key.createItemStack(1), user.player)}
        ${AQUA}На складе: ${BOLD}${entry.value} шт
        
        ${GOLD}Взять [ЛКМ] ${BOLD}${if (entry.value >= 64) 64 else entry.value} шт
        ${GOLD}Взять [ПКМ] ${BOLD}Всё - ${entry.value} шт
    """.trimIndent()

    private fun removeItems(amount: Int, entry: Map.Entry<ItemProperties, Int>, button: ReactiveButton) {
        if (user.player.inventory.firstEmpty() == -1) {
            user.player.deny("Нет места в инвентаре")
            Anime.close(user.player)
            return
        }
        blocksStorage.removeItem(entry.key, amount).run {
            user.player.inventory.addItem(entry.key.createItemStack(this.first))
            if (this.second) StorageMenu(user.player).tryExecute() else {
                button.hover = getHover(entry)
                selection.money = getFreeSpace()
            }
        }
    }
}
