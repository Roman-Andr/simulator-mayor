package me.slavita.construction.utils.extensions

import me.func.mod.world.Banners
import me.func.protocol.data.element.Banner
import org.bukkit.entity.Player

object BannersExtensions {
	fun Banners.show(player: Player, pair: Pair<Banner, Banner>) {
		show(player, pair.first)
		show(player, pair.second)
	}

	fun Banners.hide(player: Player, pair: Pair<Banner, Banner>) {
		hide(player, pair.first)
		hide(player, pair.second)
	}
}