package me.slavita.construction.ui

import me.func.mod.ui.token.Token
import me.func.mod.ui.token.TokenGroup
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

object ScoreBoardGenerator {
    fun generate(player: Player) {
        TokenGroup(
            Token.builder()
                .title("Деньги")
                .content { "${Emoji.DOLLAR} ${DARK_GREEN}${player.user.statistics.money.toMoney()}" }
                .build(),
            Token.builder()
                .title("Доход")
                .content {
                    "${Emoji.COIN} ${GREEN}${
                        player.user.income.applyBoosters(BoosterType.MONEY_BOOSTER).toMoney()
                    }"
                }
                .build(),
            Token.builder()
                .title("Уровень")
                .content { "${Emoji.UP} ${BLUE}${player.user.statistics.level}" }
                .build(),
            Token.builder()
                .title("Репутация")
                .content { "${Emoji.RUBY} ${GOLD}${player.user.statistics.reputation}" }
                .build()
        ).apply {
            subscribe(player)
        }
    }
}