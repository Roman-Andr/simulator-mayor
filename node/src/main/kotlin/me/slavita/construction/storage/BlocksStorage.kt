package me.slavita.construction.storage

import me.slavita.construction.player.User
import me.slavita.construction.world.ItemProperties

class BlocksStorage(val owner: User) {
    val blocks = hashSetOf<ItemProperties>()
}