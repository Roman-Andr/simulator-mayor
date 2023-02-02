package me.slavita.construction.ui

import me.func.mod.ui.token.Token
import me.func.mod.ui.token.TokenGroup
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.Formatter.toMoney
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.LIGHT_PURPLE

object ScoreboardGenerator {
    fun generate(user: User) {
        TokenGroup(
            Token.builder()
                .title("Монет")
                .content { "${Formatter.moneyIcon} ${GOLD}${user.data.money.toMoney()}" }
                .build(),
            Token.builder()
                .title("Доход")
                .content {
                    "${Formatter.incomeIcon} ${GREEN}${
                    user.income.applyBoosters(BoosterType.MONEY_BOOSTER).toMoney()
                    }/сек"
                }
                .build(),
            Token.builder()
                .title("Репутация")
                .content { "${Emoji.RUBY} ${LIGHT_PURPLE}${user.data.reputation.toMoney()}" }
                .build(),
            Token.builder()
                .title("Кристаллики")
                .content { "${Emoji.DONATE} ${AQUA}${user.criBalance}" }
                .build()
        ).apply {
            subscribe(user.player)
        }
    }
}
