package me.slavita.construction.action.menu.general

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.ACHIEVEMENTS_INFO
import me.slavita.construction.utils.mapM
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.RED
import org.bukkit.entity.Player

class AchievementsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {
            title = "Достижения"
            info = ACHIEVEMENTS_INFO
            description = ""
            AchievementType.values().forEach {
                user.updateAchievement(it)
            }
            storage = AchievementType.values().mapM { type ->
                user.getAchievement(type).run {
                    button {
                        title = type.title
                        item = Icons.get(type.itemKey, type.itemValue)
                        backgroundColor = GlowColor.GREEN_LIGHT
                        hover = "${GOLD}${type.placeholder.replace("%value%", "${expectValue.toMoney()}$GOLD")}"
                        hint = ""
                        description = if (level != 0) "$GREEN Уровень: ${GOLD}$level\n" else "${RED}Не начато"
                    }
                }
            }
        }
    }
}
