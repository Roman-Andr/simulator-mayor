package me.slavita.construction.storage

import com.google.gson.*
import me.func.mod.Anime
import me.func.world.Box
import me.slavita.construction.action.command.menu.StorageMenu
import me.slavita.construction.app
import me.slavita.construction.player.City
import me.slavita.construction.player.User
import me.slavita.construction.project.Project
import me.slavita.construction.project.ProjectDeserializer
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.PlayerCellDeserializer
import me.slavita.construction.world.ItemProperties
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Type

class BlocksStorage(val owner: User) {
    val blocks = hashMapOf<ItemProperties, Int>()
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
        blocks.getOrPut(itemProperties) { 0 }
        blocks[itemProperties] = blocks[itemProperties]!!.plus(amount)
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
        val json = JsonArray()
        blocksStorage.run {
            blocks.forEach { item, amonut ->
                json.add(JsonObject().apply {
                    add("item", context.serialize(item))
                    addProperty("amount", amonut)
                })
            }
        }
        return json
    }
}

class BlocksStorageDeserializer(val owner: User) : JsonDeserializer<BlocksStorage> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) = json.asJsonArray.run {
        val storage = BlocksStorage(owner)
        forEach {
            it.asJsonObject.run {
                storage.blocks[context.deserialize(get("item"), ItemProperties::class.java)] = get("amount").asInt
            }
        }
        storage
    }
}
