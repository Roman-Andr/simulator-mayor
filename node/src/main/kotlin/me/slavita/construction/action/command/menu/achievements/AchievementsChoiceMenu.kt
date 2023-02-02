package me.slavita.construction.action.command.menu.achievements

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.ACHIEVEMENTS_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.mapM
import org.bukkit.entity.Player

class AchievementsChoiceMenu(val player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {
            title = "Достижения"
            description = "Выбери нужный раздел"
            info = ACHIEVEMENTS_INFO
            storage = AchievementType.values().mapM {
                button {
                    title = it.title
                    item = Icons.get(it.itemKey, it.itemValue)
                    hint = "Выбрать"
                    click { _, _, _ ->
                        AchievementsMenu(player, it).keepHistory().tryExecute()
                    }
                }
            }
        }
    }
}
