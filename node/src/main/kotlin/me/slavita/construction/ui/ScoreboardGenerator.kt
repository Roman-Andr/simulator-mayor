package me.slavita.construction.ui

import me.func.mod.ui.token.Token
import me.func.mod.ui.token.TokenGroup
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.app
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toMoney
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

object ScoreBoardGenerator {
    fun generate(player: Player) {
        TokenGroup(
            Token.builder()
                .title("Деньги")
                .content { "${Emoji.DOLLAR} ${DARK_GREEN}${app.getUser(player).stats.money.toMoney()}" }
                .build(),
            Token.builder()
                .title("Доход")
                .content {
                    "${Emoji.COIN} ${GREEN}${
                        app.getUser(player).income.applyBoosters(BoosterType.MONEY_BOOSTER).toMoney()
                    }"
                }
                .build(),
            Token.builder()
                .title("Уровень")
                .content { "${Emoji.UP} ${BLUE}${app.getUser(player).stats.level}" }
                .build(),
            Token.builder()
                .title("Репутация")
                .content { "${Emoji.RUBY} ${GOLD}${app.getUser(player).stats.reputation}" }
                .build()
        ).apply {
            subscribe(player)
        }
    }
}