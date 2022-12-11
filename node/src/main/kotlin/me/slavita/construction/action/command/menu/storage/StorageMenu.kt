package me.slavita.construction.action.command.menu.storage

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.PlayerExtensions.deny
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class StorageMenu(player: Player) : MenuCommand(player) {
    private val blocksStorage = player.user.blocksStorage

    override fun getMenu(): Openable {
        return getBaseSelection(MenuInfo("${GREEN}${BOLD}Склад", StatsType.LEVEL, 5, 14)).apply {
            storage = mutableListOf<ReactiveButton>().apply {
                blocksStorage.blocks.forEach { entry ->
                    add(button {
                        item = entry.key.createItemStack(1)
                        hover = getHover(entry.value.amount)
                        hint = " "
                        onLeftClick { _, _, _ ->
                            if (check()) {
                                player.deny("Нет места")
                                Anime.close(player)
                                return@onLeftClick
                            }
                            blocksStorage.removeItem(entry.value, 64).apply {
                                player.inventory.addItem(entry.key.createItemStack(this.first))
                                if (this.second) StorageMenu(player).tryExecute() else {
                                    hover = getHover(entry.value.amount)
                                }
                            }
                        }
                        onRightClick { _, _, _ ->
                            if (check()) {
                                player.deny("Нет места")
                                Anime.close(player)
                                return@onRightClick
                            }
                            blocksStorage.removeItem(entry.value, entry.value.amount).apply {
                                player.inventory.addItem(entry.key.createItemStack(this.first))
                                if (this.second) StorageMenu(player).tryExecute() else {
                                    hover = getHover(entry.value.amount)
                                }
                            }
                        }
                    })
                }
            }
        }
    }

    private fun getHover(amount: Int) = """
        ${GREEN}На складе: ${BOLD}${amount} шт
        ${GOLD}Взять [ЛКМ] ${BOLD}${if (amount >= 64) 64 else amount} шт
        ${GOLD}Взять [ПКМ] ${BOLD}Всё - $amount шт
    """.trimIndent()

    private fun check(): Boolean {
        return (player.inventory.firstEmpty() == -1)
    }
}