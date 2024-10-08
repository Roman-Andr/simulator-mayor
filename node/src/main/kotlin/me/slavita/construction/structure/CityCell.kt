package me.slavita.construction.structure

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.city.City
import me.slavita.construction.utils.borderBuilder
import java.lang.reflect.Type

class CityCell(val city: City, val worldCell: WorldCell, var busy: Boolean) {
    val owner = city.owner
    val id
        get() = worldCell.id
    val box
        get() = worldCell.box
    val face
        get() = worldCell.face

    val border = borderBuilder(worldCell.box.bottomCenter, GlowColor.NEUTRAL).alpha(100).build()

    fun updateStub() {
        if (!busy) {
            worldCell.stubBuilding.show(owner.player)
            border.send(owner.player)
        }
    }

    fun setBusy() {
        busy = true
        hideGlow()
    }

    fun setFree() {
        busy = false
        border.color = GlowColor.NEUTRAL
        border.send(owner.player)
        worldCell.stubBuilding.show(owner.player)
    }

    fun hideGlow() {
        border.delete(owner.player)
        worldCell.stubBuilding.hide(owner.player)
    }
}

class CityCellSerializer : JsonSerializer<CityCell> {
    override fun serialize(cityCell: CityCell, type: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()

        cityCell.run {
            json.addProperty("cellId", id)
            json.addProperty("busy", busy)
        }

        return json
    }
}

class CityCellDeserializer(val city: City) : JsonDeserializer<CityCell> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) =
        json.asJsonObject.run {
            CityCell(city, app.mainWorld.cells[get("cellId").asInt], get("busy").asBoolean).apply {
                if (busy) {
                    hideGlow()
                }
            }
        }
}
