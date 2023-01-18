package me.slavita.construction.player

import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.project.ProjectDeserializer
import me.slavita.construction.structure.*
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.utils.PlayerExtensions.deny
import me.slavita.construction.utils.scheduler
import org.bukkit.ChatColor.*
import java.lang.reflect.Type

class City(val owner: User, val id: String, val title: String, val price: Long, var unlocked: Boolean) {
    val projects = hashSetOf<Project>()
    val cityStructures = hashSetOf<CityStructure>()
    val playerCells = arrayListOf<PlayerCell>()
    val box = app.mainWorld.map.box("city", id)

    init {
        app.mainWorld.cells.forEach {
            if (box.contains(it.box.min)) playerCells.add(PlayerCell(this, it, false))
        }
    }

    fun breakStructure() {
        if (cityStructures.size == 0) return
        cityStructures.shuffled().chunked(5)[0].forEach {
            if (it.state == CityStructureState.BROKEN || it.state == CityStructureState.NOT_READY) return
            owner.income -= it.structure.income
            it.state = CityStructureState.BROKEN
            owner.player.deny(
                """
                        ${RED}Поломка!
                        ${GRAY}Номер: ${GRAY}${it.playerCell.id}
                        ${AQUA}Название: ${GOLD}${it.structure.name}
                        ${GOLD}Локация: ${GREEN}$title
                    """.trimIndent()
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

    fun getSpawn() = box.getLabel("spawn")
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
            //json.add("cityStructures", context.serialize(cityStructures))
            json.add("playerCells", context.serialize(playerCells))
        }
        return json
    }
}

class CityDeserializer(val owner: User) : JsonDeserializer<City> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) = json.asJsonObject.run {
        City(owner, get("id").asString, get("title").asString, get("price").asLong, get("unlocked").asBoolean).apply {

            val gson = GsonBuilder()
                .registerTypeAdapter(PlayerCell::class.java, PlayerCellDeserializer(this))
                .registerTypeAdapter(Project::class.java, ProjectDeserializer(this))
                .create()

            playerCells.clear()
            get("playerCells").asJsonArray.forEach {
                playerCells.add(gson.fromJson(it, PlayerCell::class.java))
            }

            get("projects").asJsonArray.forEach {
                projects.add(gson.fromJson(it, Project::class.java))
            }
        }
    }
}
