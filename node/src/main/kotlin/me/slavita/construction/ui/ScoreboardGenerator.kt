package me.slavita.construction.ui

import me.func.mod.ui.token.Token
import me.func.mod.ui.token.TokenGroup
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.app
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
				.title("Уровень")
				.content { "${Emoji.UP} ${GREEN}${app.getUser(player).stats.level}" }
				.build(),
			Token.builder()
				.title("Строителей")
				.content { "${Emoji.MINING} ${LIGHT_PURPLE}${app.getUser(player).workers.size}" }
				.build(),
			Token.builder()
				.title("Проектов")
				.content { "${Emoji.HEART} ${DARK_PURPLE}${app.getUser(player).stats.totalProjects}" }
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