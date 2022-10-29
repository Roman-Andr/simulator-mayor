package me.slavita.construction.player.lootbox

import me.func.protocol.data.rare.DropRare
import me.slavita.construction.player.User

class WorkerLootbox(override val rare: DropRare): Lootbox {
	override val title: String
		get() = "Лутбокс рабочих"

	override fun open(user: User) {

	}
}