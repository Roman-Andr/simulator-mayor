package me.slavita.construction.storage

import me.func.mod.Anime
import me.func.world.Box
import me.slavita.construction.action.command.menu.storage.StorageMenu
import me.slavita.construction.player.User
import me.slavita.construction.world.ItemProperties
import org.bukkit.inventory.ItemStack

class BlocksStorage(val owner: User) {
    val blocks = hashMapOf<ItemProperties, ItemStack>()
    val boxes: MutableMap<String?, Box>
        get() = owner.currentCity.box.getBoxes("storagep")

    init {
        Anime.createReader("storage:open") { player, _ ->
            if (owner.uuid == player.uniqueId) StorageMenu(owner.player).tryExecute()
        }
    }

    fun inBox() = owner.currentCity.box.getBox("storage", "").contains(owner.player.location)

    fun addItem(itemStack: ItemStack) = addItem(itemStack, itemStack.getAmount())

    fun addItem(itemStack: ItemStack, amount: Int) {
        val itemProperties = ItemProperties(itemStack)
        blocks.getOrPut(itemProperties) { itemProperties.createItemStack(0) }.apply {
            this.amount += amount
        }
    }

    fun removeItem(itemStack: ItemStack, amount: Int): Int {
        val itemProperties = ItemProperties(itemStack)
        val newAmount = blocks[itemProperties]?.apply {
            this.amount -= amount
        }?.amount!!

        if (newAmount <= 0) {
            blocks.remove(itemProperties)
            return amount + newAmount
        }
        return amount
    }
}