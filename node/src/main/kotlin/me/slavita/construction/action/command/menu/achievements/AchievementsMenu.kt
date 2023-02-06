package me.slavita.construction.action.command.menu.achievements

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.ACHIEVEMENTS_INFO
import me.slavita.construction.utils.mapM
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class AchievementsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {
            title = "Достижения"
            info = ACHIEVEMENTS_INFO
            description = ""
            storage = AchievementType.values().mapM { type ->
                user.getAchievement(type).run {
                    button {
                        title = type.title
                        item = Icons.get(type.itemKey, type.itemValue)
                        backgroundColor = GlowColor.GREEN_LIGHT
                        hint = ""
                        description = (if (level != 0) "${GREEN}Уровень: ${GOLD}$level" else "") +
                                "\n$GOLD($lastValue из $expectValue)"
                    }
                }
            }
        }
    }
}
