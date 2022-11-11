package me.slavita.construction.player

import me.func.mod.Anime
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.player.lootbox.Lootbox
import me.slavita.construction.project.Project
import me.slavita.construction.storage.BlocksStorage
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.worker.Worker
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import kotlin.math.pow

class User(
	val uuid: UUID,
	var stats: Statistics,
) {
	var prepared = false
	val player = Bukkit.getPlayer(uuid)
	val city = City(this)
	val blocksStorage = BlocksStorage(this)
	var workers = hashSetOf<Worker>()
	var lootboxes = hashSetOf<Lootbox>()
	var watchableProject: Project? = null
	var income = 0L

	init {
		Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
			stats.money += income
		}, 0L, 20L)
	}

	fun tryPurchase(
		cost: Long,
		acceptAction: () -> Unit,
		denyAction: () -> Unit = { player.playSound(MusicSound.DENY) },
	) {
		if (stats.money >= cost) {
			stats.money -= cost
			acceptAction()
		} else {
			denyAction()
		}
	}

	fun addExp(exp: Long) {
		stats.experience += exp
//		if (exp / 10*2.0.pow(stats.level) > 0) {
//			stats.level += (exp / 10).toInt()
//			Anime.itemTitle(player, ItemIcons.get("other", "access"), "Новый уровень: ${stats.level}", "", 2.0)
//			Glow.animate(player, 2.0, GlowColor.GREEN)
//		}
	}

	fun canPurchase(cost: Long): Boolean {
		return stats.money >= cost
	}
}