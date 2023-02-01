package me.slavita.construction.structure

import com.google.gson.*
import me.func.mod.reactive.ReactivePlace
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.city.City
import java.lang.reflect.Type

class CityCell(val city: City, val worldCell: WorldCell, var busy: Boolean) {
    val owner = city.owner
    val id
        get() = worldCell.id
    val box
        get() = worldCell.box
    val face
        get() = worldCell.face


    private val infoGlow = ReactivePlace.builder()
        .rgb(GlowColor.NEUTRAL_LIGHT)
        .location(worldCell.box.bottomCenter.clone().apply { y -= 2.0 })
        .radius(11.0)
        .angles(120)
        .build()

    fun updateStub() {
        if (!busy) worldCell.stubBuilding.show(owner.player)
        infoGlow.delete(setOf(owner.player))
        infoGlow.send(owner.player)
    }

    fun setBusy() {
        busy = true
        hideGlow()
    }

    fun setFree() {
        busy = false
        infoGlow.send(owner.player)
        worldCell.stubBuilding.show(owner.player)
    }

    fun hideGlow() {
        infoGlow.delete(setOf(owner.player))
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
                if (busy) hideGlow()
            }
        }
}
