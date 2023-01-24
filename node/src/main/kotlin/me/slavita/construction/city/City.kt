package me.slavita.construction.city

import com.google.gson.*
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.project.Project
import me.slavita.construction.project.ProjectDeserializer
import me.slavita.construction.structure.CityStructure
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.PlayerCellDeserializer
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.utils.Alert
import me.slavita.construction.utils.Alert.button
import me.slavita.construction.utils.toYaw
import org.bukkit.ChatColor.*
import org.bukkit.Location
import org.bukkit.block.BlockFace
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
            //json.add("cityStructures", context.serialize(cityStructures))
            json.add("playerCells", context.serialize(playerCells))
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
