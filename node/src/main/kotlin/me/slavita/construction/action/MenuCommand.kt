package me.slavita.construction.action

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import org.bukkit.entity.Player

abstract class MenuCommand(player: Player, cooldown: Long = 1) : CooldownCommand(player, cooldown) {
    private var close = true

    protected abstract fun getMenu(): Openable

    override fun execute() {
        if (close) Anime.close(player)
        getMenu().open(player)
    }

    fun keepHistory(): MenuCommand {
        this.close = false
        return this
    }
}