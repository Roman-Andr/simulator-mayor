package me.slavita.construction.storage

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.storage.StorageMenu
import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.world.ItemProperties
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class BlocksStorage(val owner: User) {
	companion object {
		val boxes = app.mainWorld.map.getBoxes("storagep")
	}

	val blocks = hashMapOf<ItemProperties, ItemStack>()

	init {
		Anime.createReader("storage:open") { player, _ ->
			if (owner.uuid == player.uniqueId) StorageMenu(owner).tryExecute()
		}
	}
}