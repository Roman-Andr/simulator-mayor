package me.slavita.construction.storage

import me.func.mod.Anime
import me.func.mod.reactive.ReactivePlace
import me.func.protocol.data.color.GlowColor
import me.func.world.Box
import me.slavita.construction.action.command.menu.StorageMenu
import me.slavita.construction.player.User
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.label
import me.slavita.construction.world.ItemProperties
import org.bukkit.inventory.ItemStack

class BlocksStorage(val owner: User) {
    val blocks = hashMapOf<ItemProperties, ItemStack>()
    val boxes: MutableMap<String?, Box>
        get() = owner.currentCity.box.getBoxes("storagep")
    var upgradePlace: ReactivePlace? = null
    var limit = 100

    init {
        Anime.createReader("storage:open") { player, _ ->
            if (owner.uuid == player.uniqueId) StorageMenu(player).tryExecute()
        }
        upgradePlace = ReactivePlace.builder()
            .rgb(GlowColor.GREEN_LIGHT)
            .radius(2.0)
            .location(label("storage-upgrade")!!.toCenterLocation().apply { y -= 2.5 })
            .onEntire { player ->
                player.accept("Хранилище")
            }
            .build().apply {
                isConstant = true
            }
    }

    fun inBox() = owner.currentCity.box.getBox("storage", "").contains(owner.player.location)

    fun addItem(itemStack: ItemStack) = addItem(itemStack, itemStack.getAmount())

    fun addItem(itemStack: ItemStack, amount: Int): Int {
        val freeSpace = limit - blocks.values.sumOf { it.getAmount() }
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
        return limit - blocks.values.sumOf { it.getAmount() } >= amount
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