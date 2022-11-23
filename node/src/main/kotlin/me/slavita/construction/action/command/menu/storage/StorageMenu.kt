package me.slavita.construction.action.command.menu.storage

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class StorageMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return getBaseSelection(MenuInfo("Склад", StatsType.LEVEL, 5, 5)).apply {
            storage = mutableListOf<ReactiveButton>().apply {
                val storage = player.user.blocksStorage
                storage.blocks.forEach {
                    val item = it.value
                    add(button {
                        hint = "Взять 32 шт"
                        this.item = item
                        description = "${item.amount} шт"
                        onClick { _, _, _ ->
                            player.inventory.addItem(it.key.createItemStack(storage.removeItem(it.value, 32)))
                            StorageMenu(player).tryExecute()
                        }
                    })
                }
            }
        }
    }
}