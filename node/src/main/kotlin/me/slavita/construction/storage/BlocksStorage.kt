package me.slavita.construction.storage

import me.func.mod.Anime
import me.func.world.Box
import me.slavita.construction.action.command.menu.city.StorageMenu
import me.slavita.construction.player.User
import me.slavita.construction.utils.accept
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.GOLD
import org.bukkit.inventory.ItemStack

class BlocksStorage(val owner: User) {
    val blocks = hashMapOf<ItemProperties, ItemStack>()
    val boxes: MutableMap<String?, Box>
        get() = owner.currentCity.box.getBoxes("storagep")
    var level = 1
        set(value) {
            field = value
            limit += 100 * value
        }
    var limit: Int = 100
        private set
    val nextLimit: Int
        get() = limit + 100 * (level + 1)
    val itemsCount: Int
        get() = blocks.values.sumOf { it.getAmount() }
    val upgradePrice: Long
        get() = level * 1000L

    init {
        Anime.createReader("storage:open") { player, _ ->
            if (owner.uuid == player.uniqueId) StorageMenu(player).tryExecute()
        }
    }

    fun upgrade() {
        owner.player.accept("Вы успешно улучшили ${GOLD}Склад")
        owner.tryPurchase(upgradePrice) {
            level++
        }
    }

    fun inBox() = owner.currentCity.box.getBox("storage", "").contains(owner.player.location)

    fun addItem(itemStack: ItemStack) = addItem(itemStack, itemStack.getAmount())

    fun addItem(itemStack: ItemStack, amount: Int): Int {
        val freeSpace = limit - itemsCount
        if (freeSpace == 0) return amount
        val amountToAdd: Int
        var returnedAmount = 0
        if (freeSpace >= amount) {
            amountToAdd = amount
        } else {
            amountToAdd = freeSpace
            returnedAmount = amount - freeSpace
        }

        val itemProperties = ItemProperties(itemStack)
        blocks.getOrPut(itemProperties) { itemProperties.createItemStack(0) }.apply {
            this.amount += amountToAdd
        }
        return returnedAmount
    }

    fun hasSpace(amount: Int): Boolean {
        return limit - itemsCount >= amount
    }

    fun removeItem(itemStack: ItemStack, amount: Int): Pair<Int, Boolean> {
        val itemProperties = ItemProperties(itemStack)
        val newAmount = blocks[itemProperties]?.apply {
            this.amount -= amount
        }?.amount!!

        if (newAmount <= 0) {
            blocks.remove(itemProperties)
            return Pair(amount + newAmount, true)
        }
        return Pair(amount, false)
    }
}