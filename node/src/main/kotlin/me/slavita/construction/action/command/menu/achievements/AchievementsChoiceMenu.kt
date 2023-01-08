package me.slavita.construction.action.command.menu.achievements

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.mapM
import org.bukkit.entity.Player

class AchievementsChoiceMenu(val player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {
            title = "Достижения"
            description = "Выбери нужный раздел"
            storage = AchievementType.values().mapM {
                button {
                    title = it.title
                    item = ItemIcons.get(it.itemKey, it.itemValue)
                    hint = "Выбрать"
                    onClick { _, _, _ ->
                        AchievementsMenu(player, it).keepHistory().tryExecute()
                    }
                }
            }
        }
    }
}