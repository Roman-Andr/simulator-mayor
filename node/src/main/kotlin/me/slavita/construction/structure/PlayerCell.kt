package me.slavita.construction.structure

import com.google.gson.*
import me.slavita.construction.app
import me.slavita.construction.player.City
import java.lang.reflect.Type

class PlayerCell(val city: City, val cell: Cell, var busy: Boolean) {

    val owner = city.owner
    val id
        get() = cell.id
    val box
        get() = cell.box
    val face
        get() = cell.face

    fun updateStub() {
        if (!busy) cell.stubBuilding.show(owner.player)
    }

    fun setBusy() {
        busy = true
        cell.stubBuilding.hide(owner.player)
    }
}

class PlayerCellSerializer : JsonSerializer<PlayerCell> {
    override fun serialize(playerCell: PlayerCell, type: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()

        playerCell.run {
            json.addProperty("cellId", id)
            json.addProperty("busy", busy)
        }

        return json
    }
}

class PlayerCellDeserializer(val city: City) : JsonDeserializer<PlayerCell> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) = json.asJsonObject.run {
        PlayerCell(city, app.mainWorld.cells[get("cellId").asInt], get("busy").asBoolean)
    }
}
