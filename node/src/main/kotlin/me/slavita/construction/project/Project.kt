package me.slavita.construction.project

import me.slavita.construction.player.City
import me.slavita.construction.player.sound.Music.playSound
import me.slavita.construction.player.sound.MusicSound
import me.slavita.construction.reward.Reward
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.structure.tools.StructureState
import org.bukkit.ChatColor.*
import me.slavita.construction.ui.Formatter.toLevel
import me.slavita.construction.ui.HumanizableValues
import org.bukkit.ChatColor

open class Project(
    val city: City,
    var id: Int,
    val rewards: List<Reward>,
) {
    lateinit var structure: BuildingStructure
    val owner = city.owner
    val timeLast: Int //todo: use this
        get() = 0

    fun initialize() {
        var projectsCount = 0
        owner.data.cities.forEach {
            projectsCount += it.projects.size
        }
        id = owner.data.totalProjects + projectsCount
    }

    fun start() {
        structure.startBuilding(this)
        owner.player.playSound(MusicSound.SUCCESS3)
    }

    open fun onEnter() {
        when (structure.state) {
            StructureState.BUILDING -> structure.showVisual()
            StructureState.FINISHED -> {
                finish()
                structure.cityStructure!!.state = CityStructureState.FUNCTIONING
                structure.cityStructure!!.startIncome()
                city.finishProject(this@Project)
            }

            else                    -> {}
        }
    }

    fun finish() {
        structure.claimed()
        rewards.forEach { reward ->
            reward.getReward(owner)
        }
        owner.player.playSound(MusicSound.UI_CLICK)
        owner.data.totalProjects++
    }

    fun onLeave() {
        when (structure.state) {
            StructureState.BUILDING -> structure.hideVisual()
            else                    -> {}
        }
    }

    override fun toString() = """
Информация про проект:
  ${AQUA}ID: $id
  ${AQUA}Награды:
  ${rewards.joinToString("\n  ") { it.toString() }}
""".trimIndent()
}

class ProjectSerializer : JsonSerializer<Project> {
    override fun serialize(project: Project, type: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()

        project.run {
            json.addProperty("id", id)
            json.add("structure", context.serialize(structure))
            json.add("rewards", context.serialize(rewards))
        }

        return json
    }
}

class ProjectDeserializer(val city: City) : JsonDeserializer<Project> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) = json.asJsonObject.run {
        val project = Project(city, get("id").asInt, context.deserialize(get("rewards"), List::class.java))

        val gson = GsonBuilder()
            .registerTypeAdapter(BuildingStructure::class.java, BuildingStructureDeserializer(project))
            .create()

        project.structure = gson.fromJson(get("structure"), BuildingStructure::class.java)

        project
    }
}
