package me.slavita.construction.player.lootbox

import me.func.mod.Anime
import me.func.protocol.data.rare.DropRare
import me.slavita.construction.player.User
import java.util.*

class BlocksLootbox(override val rare: DropRare) : Lootbox {
    override val title: String
        get() = "Лутбокс блоков"

    override fun open(user: User) {

    }
}