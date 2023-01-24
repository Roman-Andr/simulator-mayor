package me.slavita.construction.action

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.slavita.construction.player.User
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

abstract class MenuCommand(override val user: User, cooldown: Long = 1) : CooldownCommand(user, cooldown) {
    constructor(player: Player, cooldown: Long = 1) : this(player.user, cooldown)

    private var close = true

    protected abstract fun getMenu(): Openable

    override fun execute() {
        if (close) Anime.close(user.player)
        getMenu().open(user.player)
    }

    fun keepHistory(): MenuCommand {
        this.close = false
        return this
    }
}
