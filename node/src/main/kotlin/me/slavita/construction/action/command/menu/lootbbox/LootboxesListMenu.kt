package me.slavita.construction.action.command.menu.lootbbox

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.extensions.Extensions.getColor
import org.bukkit.entity.Player

class LootboxesListMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@{
            return getBaseSelection(MenuInfo("Список лутбоксов", StatsType.MONEY, 3, 3)).apply {
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    lootboxes.forEach { lootbox ->
                        this@storage.add(button {
                            item = ItemIcons.get("other", "new_lvl_rare_close")
                            title = lootbox.title
                            hint = "Открыть"
                            backgroundColor = lootbox.rare.getColor()
                            onClick { _, _, _ ->
                                OpenLootbox(this@user, lootbox).tryExecute()
                            }
                        })
                    }
                }
            }
        }
    }
}