package me.slavita.construction.action

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.MenuInfo
import me.slavita.construction.ui.StatsType
import org.bukkit.entity.Player

abstract class MenuCommand(player: Player) : CooldownCommand(player, 5) {
    private var close = true

    protected abstract fun getMenu(): Openable

    override fun execute() {
        if (close) Anime.close(player)
        getMenu().open(player)
    }

    fun closeAll(close: Boolean): MenuCommand {
        this.close = close
        return this
    }

    fun get(info: MenuInfo): Selection {
        info.run {
            return Selection(
                title = title,
                vault = type.vault,
                money = "Ваш ${type.title} ${when (type){
                    StatsType.MONEY -> app.getUser(player).stats.money.toMoney()
                    StatsType.LEVEL -> app.getUser(player).stats.level
                }}",
                rows = rows,
                columns = columns
            )
        }
    }
}