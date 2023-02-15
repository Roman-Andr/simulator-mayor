package me.slavita.construction.structure

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import me.func.unit.Building
import me.func.world.Box
import me.slavita.construction.app
import me.slavita.construction.city.City
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.structure.tools.CityStructureVisual
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.notify
import me.slavita.construction.utils.user
import me.slavita.construction.world.AmountItemProperties
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player
import java.lang.reflect.Type
import java.util.UUID

class CityStructure(val owner: Player, val structure: Structure, val cell: CityCell) {

    val structureBox = Box(app.structureMap, structure.box.min, structure.box.max, "", "")
    val building = Building(UUID.randomUUID(), "", "", 0.0, 0.0, 0.0, structureBox)
    var state = CityStructureState.NOT_READY
    val visual = CityStructureVisual(this)
    var repairBlocks: HashMap<ItemProperties, Int> = hashMapOf()
    var targetBlocks: HashMap<ItemProperties, Int> = HashMap(structure.blocks)

    init {
        building.allocate(cell.box.min.clone().add(11.0, 0.0, 11.0))
        building.show(owner)
        visual.update()
    }

    fun remove() {
        building.hide(owner)
        building.deallocate()
        visual.delete()
        cell.setFree()
        owner.user.data.cities.forEach { it.cityStructures.remove(this) }
    }

    fun repair() {
        state = CityStructureState.FUNCTIONING
        owner.user.updateIncome()
        repairBlocks = hashMapOf()
        owner.player.notify(
            """
                ${GOLD}Отремонтировано
                ${GRAY}Номер: ${GRAY}${cell.id}
                ${AQUA}Название: ${GOLD}${structure.name}
                ${GREEN}Локация: ${GREEN}${cell.city.title}
            """.trimIndent()
        )
        visual.update()
    }

    override fun toString() = """
        ${GOLD}Информация про проект:
          Номер: ${GRAY}${cell.id}
          Название: ${GOLD}${structure.name}
          Доход: ${(if (state == CityStructureState.FUNCTIONING) structure.income else 0.toMoneyIcon())}
          Локация: ${GREEN}${cell.city.title}
    """.trimIndent()
}

class CityStructureSerializer : JsonSerializer<CityStructure> {
    override fun serialize(cityStructure: CityStructure, type: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()
        cityStructure.run {
            json.addProperty("structureId", structure.id)
            json.addProperty("cityCellId", cell.id)
            json.addProperty("state", state.name)

            val repairBlocks = JsonArray()
            this.repairBlocks.forEach { repairBlocks.add(context.serialize(AmountItemProperties(it.key, it.value))) }
            json.add("repairBlocks", repairBlocks)

            val targetBlocks = JsonArray()
            this.targetBlocks.forEach { targetBlocks.add(context.serialize(AmountItemProperties(it.key, it.value))) }
            json.add("targetBlocks", repairBlocks)
        }
        return json
    }
}

class CityStructureDeserializer(val city: City) : JsonDeserializer<CityStructure> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) =
        json.asJsonObject.run {
            CityStructure(
                city.owner.player,
                Structures.structures[get("structureId").asInt],
                city.cityCells[get("cityCellId").asInt]
            ).apply {
                get("repairBlocks").asJsonArray.forEach { // TODO: duplicates in building structure deserializer
                    val item = context.deserialize<AmountItemProperties>(it, AmountItemProperties::class.java)
                    repairBlocks[item] = item.amount
                }

                targetBlocks.clear()
                get("targetBlocks").asJsonArray.forEach {
                    val item = context.deserialize<AmountItemProperties>(it, AmountItemProperties::class.java)
                    targetBlocks[item] = item.amount
                }

                state = CityStructureState.valueOf(get("state").asString)
            }
        }
}
