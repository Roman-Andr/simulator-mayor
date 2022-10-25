package me.slavita.construction.ui

import me.func.mod.Anime
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.entity.Player

object Animations {
	fun buildingFinished(player: Player) {
		Glow.animate(player, 0.1, GlowColor.GREEN)
		Anime.itemTitle(player, ItemIcons.get("other", "access"), "Постройка\nзавершена", null, 0.5)
	}
}