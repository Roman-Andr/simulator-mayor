package me.slavita.construction.action.command.menu.storage

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class StorageMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return getBaseSelection(MenuInfo("${GREEN}${BOLD}Склад", StatsType.LEVEL, 5, 14)).apply {
            storage = mutableListOf<ReactiveButton>().apply {
                val blocksStorage = player.user.blocksStorage
                storage = blocksStorage.blocks.map {
                    button {
                        hover = """
                            На складе ${it.value.amount} шт
                            Взять ${if (it.value.amount >= 32) 32 else it.value.amount} шт
                        """.trimIndent()
                        hint = " "
                        item = it.value
                        onClick { _, _, _ ->
                            player.inventory.addItem(it.key.createItemStack(blocksStorage.removeItem(it.value, 32)))
                            StorageMenu(player).tryExecute()
                        }
                    }
                }.toMutableList()
            }
        }
    }
}