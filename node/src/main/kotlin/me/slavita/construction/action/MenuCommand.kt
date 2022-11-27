package me.slavita.construction.action

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.bank.Bank
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.user
import org.bukkit.Material
import org.bukkit.entity.Player

abstract class MenuCommand(player: Player) : CooldownCommand(player, 1) {
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

    fun getBaseSelection(info: MenuInfo): Selection =
        selection {
            title = info.title
            vault = info.type.vault
            rows = info.rows
            columns = info.columns
            money = "Ваш ${info.type.title} ${
                when (info.type) {
                    StatsType.MONEY  -> player.user.statistics.money.toMoney()
                    StatsType.LEVEL  -> player.user.statistics.level
                    StatsType.CREDIT -> Bank.playersData[player.uniqueId]!!.sumOf { it.creditValue }.toMoney()
                }
            }"
        }

    fun getEmptyButton(): ReactiveButton =
        button {
            material(Material.AIR)
            hint = ""
            enabled = false
        }
}