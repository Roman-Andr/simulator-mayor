package me.slavita.construction.action.command.menu

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.prepare.TagsPrepare
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class SettingsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        return Choicer(title = "${GREEN}${BOLD}Настройки", description = "Настройка игровых аспектов").apply {
            storage = mutableListOf(
                button {
                    title = "Показ тега"
                    updateButton(this)
                    onClick { _, _, button ->
                        player.user.data.settings.apply { this.tagShow = !this.tagShow }
                        updateButton(button)
                        TagsPrepare.prepare(player.user)
                    }
                }
            )
        }
    }

    private fun updateButton(button: ReactiveButton) {
        button.apply {
            if (player.user.data.settings.tagShow) {
                hint = "Выключить"
                item = ItemIcons.get("other", "access")
                backgroundColor = GlowColor.GREEN
            } else {
                hint = "Включить"
                item = ItemIcons.get("other", "cancel")
                backgroundColor = GlowColor.NEUTRAL
            }
        }
    }
}