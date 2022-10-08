package me.slavita.construction.action

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import org.bukkit.entity.Player

abstract class MenuCommand(player: Player) : CooldownCommand(player, 5) {
    private var close = true

    protected abstract fun getMenu(): Openable

    fun closeAll(close: Boolean): MenuCommand {
        this.close = close
        return this
    }

    override fun execute() {
        if (close) Anime.close(player)
        getMenu().open(player)
    }
}