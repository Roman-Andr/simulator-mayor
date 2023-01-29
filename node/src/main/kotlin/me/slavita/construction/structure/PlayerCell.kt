package me.slavita.construction.structure

import com.google.gson.*
import me.func.atlas.Atlas
import me.func.mod.Anime
import me.func.mod.reactive.ReactiveBanner
import me.func.mod.reactive.ReactivePlace
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.city.City
import me.slavita.construction.utils.loadBanner
import me.slavita.construction.utils.loadBannerFromConfig
import me.slavita.construction.utils.toYaw
import java.lang.reflect.Type

class PlayerCell(val city: City, val cell: Cell, var busy: Boolean) {

    val owner = city.owner
    val id
        get() = cell.id
    val box
        get() = cell.box
    val face
        get() = cell.face
    private val infoGlow = ReactivePlace.builder()
        .rgb(GlowColor.NEUTRAL_LIGHT)
        .location(box.bottomCenter.clone().apply { y -= 2.0 })
        .radius(11.0)
        .angles(120)
        .build()

    fun updateStub() {
        if (!busy) cell.stubBuilding.show(owner.player)
        infoGlow.delete(setOf(owner.player))
        infoGlow.send(owner.player)
    }

    fun setBusy() {
        busy = true
        infoGlow.delete(setOf(owner.player))
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
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) =
        json.asJsonObject.run {
            PlayerCell(city, app.mainWorld.cells[get("cellId").asInt], get("busy").asBoolean)
        }
}
