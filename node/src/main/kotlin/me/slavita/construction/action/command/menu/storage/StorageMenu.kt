package me.slavita.construction.action.command.menu.storage

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.player.User
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import org.bukkit.inventory.ItemStack

class StorageMenu(val user: User) : MenuCommand(user.player) {
    override fun getMenu(): Openable {
        return getBaseSelection(MenuInfo("Склад", StatsType.LEVEL, 5, 5)).apply {
            storage = mutableListOf<ReactiveButton>().apply {
                user.blocksStorage.blocks.forEach {
                    val item = it.value
                    add(button {
                        hint = "Взять"
                        this.item = item
                        onRightClick { _, _, _ ->
                            val amount = if (this.item!!.amount - 32 <= 0) this.item!!.amount else this.item!!.amount - 32
                            player.inventory.addItem(it.key.createItemStack(amount))
                            this.item!!.amount -= amount
                        }
                    })
                }
            }
        }
    }
}