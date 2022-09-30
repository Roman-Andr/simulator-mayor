package me.slavita.construction.action

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import org.bukkit.entity.Player

abstract class MenuCommand(player: Player) : CooldownCommand(player, 5) {
    protected abstract fun getMenu(): Openable

    override fun execute() {
        Anime.close(player)
        getMenu().open(player)
    }
}