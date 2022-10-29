package me.slavita.construction.player.lootbox

import me.func.protocol.data.rare.DropRare
import me.slavita.construction.player.User

interface Lootbox {
	val title: String
	val rare: DropRare
	fun open(user: User)
}