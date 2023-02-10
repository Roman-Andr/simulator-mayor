package me.slavita.construction.action.command.menu.general

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.prepare.TagsPrepare
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.SETTINGS_INFO
import me.slavita.construction.utils.click
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class SettingsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {
            title = "${GREEN}${BOLD}Настройки"
            description = "Настройка игровых аспектов"
            info = SETTINGS_INFO
            storage = mutableListOf(
                button {
                    title = "Теги"
                    hover = """
                        ${AQUA}Теги:
                          Позволяет настроить отображение 
                          вашего тега
                    """.trimIndent()
                    hint = "Выбрать"
                    updateButton(this, user.data.settings.tagShow)
                    click { _, _, button ->
                        user.data.settings.run {
                            tagShow = !tagShow
                            updateButton(button, tagShow)
                        }
                        TagsPrepare.prepare(user)
                    }
                }
            )
        }
    }

    private fun updateButton(button: ReactiveButton, active: Boolean) {
        button.apply {
            if (active) {
                item = Icons.get("other", "access")
                backgroundColor = GlowColor.GREEN
            } else {
                item = Icons.get("other", "cancel")
                backgroundColor = GlowColor.NEUTRAL
            }
        }
    }
}
