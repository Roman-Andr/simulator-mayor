package me.slavita.construction.action.command.menu

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.*
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class StorageMenu(player: Player) : MenuCommand(player) {
    private val blocksStorage = player.user.blocksStorage

    override fun getMenu(): Openable {
        return getBaseSelection(MenuInfo("${GREEN}${BOLD}Склад", StatsType.LEVEL, 5, 14), user).apply {
            storage = blocksStorage.blocks.mapM { entry ->
                button {
                    item = entry.key.createItemStack(1)
                    hover = getHover(entry)
                    info = getStorageInfo()
                    hint = " "
                    onLeftClick { _, _, _ ->
                        removeItems(64, entry, this)
                    }
                    onRightClick { _, _, _ ->
                        removeItems(entry.value.amount, entry, this)
                    }
                }
            }
        }
    }

    private fun getHover(entry: Map.Entry<ItemProperties, ItemStack>) = """
        ${GREEN}${LanguageHelper.getItemDisplayName(entry.key.createItemStack(1), user.player)}
        ${AQUA}На складе: ${BOLD}${entry.value.amount} шт
        
        ${GOLD}Взять [ЛКМ] ${BOLD}${if (entry.value.amount >= 64) 64 else entry.value.amount} шт
        ${GOLD}Взять [ПКМ] ${BOLD}Всё - ${entry.value.amount} шт
    """.trimIndent()

    private fun removeItems(amount: Int, entry: Map.Entry<ItemProperties, ItemStack>, button: ReactiveButton) {
        if (user.player.inventory.firstEmpty() == -1) {
            user.player.deny("Нет места")
            Anime.close(user.player)
            return
        }
        blocksStorage.removeItem(entry.value, amount).apply {
            user.player.inventory.addItem(entry.key.createItemStack(this.first))
            if (this.second) StorageMenu(user.player).tryExecute() else {
                button.hover = getHover(entry)
            }
        }
    }
}