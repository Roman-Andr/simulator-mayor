package me.slavita.construction.city

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.city.project.Project
import me.slavita.construction.city.project.ProjectDeserializer
import me.slavita.construction.player.User
import me.slavita.construction.structure.CityCell
import me.slavita.construction.structure.CityCellDeserializer
import me.slavita.construction.structure.CityStructure
import me.slavita.construction.structure.CityStructureDeserializer
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.utils.Alert
import me.slavita.construction.utils.Alert.button
import me.slavita.construction.utils.toYaw
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.RED
import org.bukkit.Location
import org.bukkit.block.BlockFace
import java.lang.reflect.Type

class City(val owner: User, val id: String, val title: String, val price: Long, var unlocked: Boolean) {
    val projects = hashSetOf<Project>()
    val cityStructures = hashSetOf<CityStructure>()
    val cityCells = arrayListOf<CityCell>()
    val box = app.mainWorld.map.box("city", id)

    init {
        app.mainWorld.cells.forEach {
            if (box.contains(it.box.min)) cityCells.add(CityCell(this, it, false))
        }
    }

    fun breakStructure() {
        if (cityStructures.size == 0) return

        val targetStructures =
            cityStructures.filter { it.state != CityStructureState.BROKEN && it.state != CityStructureState.NOT_READY }
        if (targetStructures.isEmpty()) return
        targetStructures.shuffled()[0].let {
            owner.income -= it.structure.income
            it.state = CityStructureState.BROKEN

            Alert.send(
                owner.player,
                """
                    ${RED}Поломка
                    ${GRAY}Номер: ${GRAY}${it.cell.id}
                    ${AQUA}Название: ${GOLD}${it.structure.name}
                    ${GOLD}Локация: ${GREEN}$title
                """.trimIndent(),
                5000,
                GlowColor.RED,
                GlowColor.RED_MIDDLE,
                null,
                button("Понятно", "/ok", GlowColor.GREEN),
            )

            it.visual.update()
        }
    }

    fun addStructure(cityStructure: CityStructure): CityStructure {
        cityStructures.add(cityStructure)
        return cityStructure
    }

    fun addProject(project: Project) {
        projects.add(project)
    }

    fun finishProject(project: Project) {
        projects.remove(project)
    }

    fun getSpawn(): Location = box.getLabel("spawn")!!.toCenterLocation().apply {
        yaw = BlockFace.EAST.toYaw()
        y -= 0.5
    }
}

class CitySerializer : JsonSerializer<City> {
    override fun serialize(city: City, type: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()
        city.apply {
            json.addProperty("id", id)
            json.addProperty("title", title)
            json.addProperty("price", price)
            json.addProperty("unlocked", unlocked)
            json.add("projects", context.serialize(projects))
            json.add("cityStructures", context.serialize(cityStructures))
            json.add("cityCells", context.serialize(cityCells))
        }
        return json
    }
}

class CityDeserializer(val owner: User) : JsonDeserializer<City> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) =
        json.asJsonObject.run {
            City(
                owner,
                get("id").asString,
                get("title").asString,
                get("price").asLong,
                get("unlocked").asBoolean
            ).apply {

                val gson = GsonBuilder()
                    .registerTypeAdapter(CityCell::class.java, CityCellDeserializer(this))
                    .registerTypeAdapter(Project::class.java, ProjectDeserializer(this))
                    .registerTypeAdapter(CityStructure::class.java, CityStructureDeserializer(this))
                    .create()

                cityCells.clear()
                get("cityCells").asJsonArray.forEach {
                    cityCells.add(gson.fromJson(it, CityCell::class.java))
                }

                get("projects").asJsonArray.forEach {
                    projects.add(gson.fromJson(it, Project::class.java))
                }

                get("cityStructures").asJsonArray.forEach {
                    cityStructures.add(gson.fromJson(it, CityStructure::class.java))
                }
            }
        }
}
