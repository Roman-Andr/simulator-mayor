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

object ScoreboardGenerator {
    fun generate(player: Player) {
        TokenGroup(
            Token.builder()
                .title("Монет")
                .content { "${Emoji.COIN} ${GOLD}${player.user.statistics.money.toMoney()}" }
                .build(),
            Token.builder()
                .title("Доход")
                .content {
                    "${Emoji.DOLLAR} ${GREEN}${
                        player.user.income.applyBoosters(BoosterType.MONEY_BOOSTER).toMoney()
                    }/сек"
                }
                .build(),
            Token.builder()
                .title("Репутация")
                .content { "${Emoji.RUBY} ${LIGHT_PURPLE}${player.user.statistics.reputation}" }
                .build(),
            Token.builder()
                .title("Кристаллики")
                .content { "${Emoji.DONATE} ${AQUA}${player.user.criBalance}" }
                .build()
        ).apply {
            subscribe(player)
        }
    }
}