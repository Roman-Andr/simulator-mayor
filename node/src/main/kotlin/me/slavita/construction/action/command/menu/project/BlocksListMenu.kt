package me.slavita.construction.action.command.menu.project

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.world.ItemProperties
import org.bukkit.entity.Player

class BlocksListMenu(player: Player, val structure: Structure) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Selection(title = "Список материалов", rows = 5, columns = 4,
                storage = mutableListOf<ReactiveButton>().apply {
                    hashSetOf<ItemProperties>().apply {
                        structure.box.forEachBukkit { this.add(ItemProperties.fromBlock(it)) }
                    }.forEach {
                        add(button {
                            hover = it.createItemStack(1).i18NDisplayName
                            item = it.createItemStack(1)
                        })
                    }
                }
            )
        }
    }
}