package me.slavita.construction.city.storage

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import me.func.mod.Anime
import me.func.world.Box
import me.slavita.construction.action.command.menu.city.StorageMenu
import me.slavita.construction.player.User
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.runAsync
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.GOLD
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Type

class BlocksStorage(val owner: User) {
    val blocks = hashMapOf<ItemProperties, Int>()
    val boxes: MutableMap<String?, Box>
        get() = owner.currentCity.box.getBoxes("storagep")
    var level = 1
        set(value) {
            field = value
            limit += 100 * value
            owner.updateAchievement(AchievementType.STORAGE)
        }
    var limit: Int = 100
        private set
    val nextLimit: Int
        get() = limit + 100 * (level + 1)
    val itemsCount: Int
        get() = blocks.values.sumOf { it }
    val upgradePrice: Long
        get() = level * 1000L

    init {
        Anime.createReader("storage:open") { player, _ ->
            if (owner.uuid == player.uniqueId) StorageMenu(player).tryExecute()
        }
    }

    fun upgrade() {
        owner.tryPurchase(upgradePrice) {
            owner.player.accept("Вы успешно улучшили ${GOLD}Склад")
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
        blocks.getOrPut(itemProperties) { 0 }
        blocks[itemProperties] = blocks[itemProperties]!!.plus(amountToAdd)
        return returnedAmount
    }

    fun hasSpace(amount: Int): Boolean {
        return limit - itemsCount >= amount
    }

    fun removeItem(itemProperties: ItemProperties, amount: Int): Pair<Int, Boolean> {
        blocks[itemProperties] = blocks[itemProperties]!!.minus(amount)
        val newAmount = blocks[itemProperties]!!

        if (newAmount <= 0) {
            blocks.remove(itemProperties)
            return Pair(amount + newAmount, true)
        }
        return Pair(amount, false)
    }
}

class BlocksStorageSerializer : JsonSerializer<BlocksStorage> {
    override fun serialize(blocksStorage: BlocksStorage, type: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()
        val blocksJson = JsonArray()
        blocksStorage.run {
            blocks.forEach { (item, amount) ->
                blocksJson.add(
                    JsonObject().apply {
                        add("item", context.serialize(item))
                        addProperty("amount", amount)
                    }
                )
            }
            json.addProperty("level", level)
            json.add("blocks", blocksJson)
        }
        return json
    }
}

class BlocksStorageDeserializer(val owner: User) : JsonDeserializer<BlocksStorage> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) =
        json.asJsonObject.run {
            val storage = BlocksStorage(owner).apply {
                runAsync(2) {
                    level = get("level").asInt
                }
            }
            get("blocks").asJsonArray.run {
                forEach {
                    it.asJsonObject.run {
                        storage.blocks[context.deserialize(get("item"), ItemProperties::class.java)] =
                            get("amount").asInt
                    }
                }
            }
            storage
        }
}
