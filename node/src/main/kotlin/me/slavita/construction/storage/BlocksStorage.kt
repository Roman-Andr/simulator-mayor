package me.slavita.construction.storage

import me.func.mod.Anime
import me.func.world.Box
import me.slavita.construction.action.command.menu.StorageMenu
import me.slavita.construction.player.User
import me.slavita.construction.world.ItemProperties
import org.bukkit.inventory.ItemStack

class BlocksStorage(val owner: User) {
    val blocks = hashMapOf<ItemProperties, ItemStack>()
    val boxes: MutableMap<String?, Box>
        get() = owner.currentCity.box.getBoxes("storagep")

    init {
        Anime.createReader("storage:open") { player, _ ->
            if (owner.uuid == player.uniqueId) StorageMenu(player).tryExecute()
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

    fun getAmount(itemStack: ItemStack): Int {
        blocks.entries.find { it.value == ItemProperties(itemStack) }.let {
            if (it == null) return -1
            return it.value.amount
        }
    }

    fun removeItem(itemStack: ItemStack, amount: Int): Pair<Int, Boolean> {
        var removed = false
        val itemProperties = ItemProperties(itemStack)
        val newAmount = blocks[itemProperties]?.apply {
            this.amount -= amount
        }?.amount!!

        if (newAmount <= 0) {
            removed = true
            blocks.remove(itemProperties)
            return Pair(amount + newAmount, removed)
        }
        return Pair(amount, removed)
    }
}