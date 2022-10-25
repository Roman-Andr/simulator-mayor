package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.player.User
import org.bukkit.GameMode

object PlayerWorldPrepare : IPrepare {
	override fun prepare(user: User) {
		user.player.teleport(app.mainWorld.getSpawn())
		user.player.gameMode = GameMode.ADVENTURE
	}
}