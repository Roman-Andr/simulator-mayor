package me.slavita.construction.utils

import me.func.mod.ui.scoreboard.ScoreBoard
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.app
import me.slavita.construction.utils.Formatter.toMoneyIcon
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object ScoreBoardGenerator {
    fun generate(player: Player) {
        ScoreBoard.builder()
            .key("scoreboard")
            .header("Стройка")
            .dynamic("Монеты") {
                return@dynamic app.getUser(it).stats.money.toMoneyIcon()
            }
            .dynamic("Уровень") {
                return@dynamic "${app.getUser(it).stats.level}${ChatColor.WHITE} ${Emoji.UP}"
            }
            .empty()
            .dynamic("Строителей") {
                return@dynamic "${app.getUser(it).workers.size}"
            }
            .empty()
            .dynamic("Проектов") {
                return@dynamic "${app.getUser(it).stats.totalProjects}"
            }
            .dynamic("Репутация") {
                return@dynamic "${app.getUser(it).stats.reputation}"
            }
            .build().apply {
                ScoreBoard.subscribe("scoreboard", player)
                show(player)
            }
    }
}