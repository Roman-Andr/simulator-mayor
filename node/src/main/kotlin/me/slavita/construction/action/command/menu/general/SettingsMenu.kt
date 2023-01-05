package me.slavita.construction.action.command.menu.general

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.prepare.TagsPrepare
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.getSettingInfo
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class SettingsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return choicer {
            title = "${GREEN}${BOLD}Настройки"
            description = "Настройка игровых аспектов"
            info = getSettingInfo()
            storage = mutableListOf(
                button {
                    title = "Показ тега"
                    hint = "Выбрать"
                    updateButton(this)
                    onClick { _, _, button ->
                        user.data.settings.apply { this.tagShow = !this.tagShow }
                        updateButton(button)
                        TagsPrepare.prepare(user)
                    }
                }
            )
        }
    }

    private fun updateButton(button: ReactiveButton) {
        button.apply {
            if (user.data.settings.tagShow) {
                item = ItemIcons.get("other", "access")
                backgroundColor = GlowColor.GREEN
            } else {
                item = ItemIcons.get("other", "cancel")
                backgroundColor = GlowColor.NEUTRAL
            }
        }
    }
}