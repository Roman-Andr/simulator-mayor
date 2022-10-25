package me.slavita.construction.storage

import me.slavita.construction.player.User
import org.bukkit.inventory.ItemStack

class BlocksStorage(val owner: User) {
    var blocks = listOf<ItemStack>()
}