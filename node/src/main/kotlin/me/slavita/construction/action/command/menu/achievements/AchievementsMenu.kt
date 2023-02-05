package me.slavita.construction.action.command.menu.achievements

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.ACHIEVEMENTS_INFO
import me.slavita.construction.utils.ACHIEVEMENT_LEVELS_COUNT
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.size
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class AchievementsMenu(player: Player, val type: AchievementType) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return selection {
            title = type.title
            info = ACHIEVEMENTS_INFO
            size(4, 3)

            val level = user.data.achievements.find { it.type == type }!!.level
            storage = (1..ACHIEVEMENT_LEVELS_COUNT).mapM { value ->
                button {
                    title = "${BOLD}${GREEN}${type.title} #$value"
                    item = Icons.get(type.itemKey, type.itemValue, value < level)
                    backgroundColor = if (value < level) GlowColor.GREEN else GlowColor.BLUE
                    description = type.placeholder.replace("%value%", type.formula(value).toString())
                    hint = if (value < level) "Получено" else " "
                }
            }
        }
    }
}
